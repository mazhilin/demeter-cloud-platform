package com.demeter.cloud.console.utils;

import com.demeter.cloud.console.annotation.RequiresPermissionsDesc;
import com.demeter.cloud.console.web.Permission;
import com.demeter.cloud.console.web.PermissionData;
import com.demeter.cloud.utils.CheckEmptyUtil;
import com.demeter.cloud.model.exception.BusinessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>封装Dcloud项目ConsolePermissionUtil类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2021-02-17 16:02
 * @version 1.0.0
 * <p>Copyright © 2018-2021 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class ConsolePermissionUtil {

    /**
     * 查询系统权限数据集合
     *
     * @param permissions 权限数据集合
     * @return 返回集合
     */
    public static List<PermissionData> permissionDataList(List<Permission> permissions) {
        List<PermissionData> root = Lists.newLinkedList();
        for (Permission permission : permissions) {
            RequiresPermissions requiresPermissions = permission.getRequiresPermissions();
            RequiresPermissionsDesc requiresPermissionsDesc = permission.getRequiresPermissionsDesc();
            String api = permission.getApi();

            String[] menus = requiresPermissionsDesc.menu();
            if (CheckEmptyUtil.isNotEmpty(menus) && menus.length != 2) {
                throw new BusinessException("目前只支持两级菜单!");
            }
            String rootMenu = menus[0];
            PermissionData rootData = null;
            for (PermissionData permissionData : root) {
                if (permissionData.getLabel().equals(rootMenu)) {
                    rootData = permissionData;
                    break;
                }
            }
            if (rootData == null) {
                rootData = new PermissionData();
                rootData.setId(rootMenu);
                rootData.setLabel(rootMenu);
                rootData.setChildren(Lists.newLinkedList());
                root.add(rootData);
            }
            String itemMenu = menus[1];
            PermissionData itemData = null;
            for (PermissionData permissionData : rootData.getChildren()) {
                if (permissionData.getLabel().equals(itemMenu)) {
                    itemData = permissionData;
                    break;
                }
            }
            if (itemData == null) {
                itemData = new PermissionData();
                itemData.setId(itemMenu);
                itemData.setLabel(itemMenu);
                itemData.setChildren(Lists.newLinkedList());
                rootData.getChildren().add(itemData);
            }

            String button = requiresPermissionsDesc.button();
            PermissionData buttonData = null;
            for (PermissionData permissionData : itemData.getChildren()) {
                if (permissionData.getLabel().equals(button)) {
                    buttonData = permissionData;
                    break;
                }
            }
            if (buttonData == null) {
                buttonData = new PermissionData();
                buttonData.setId(requiresPermissions.value()[0]);
                buttonData.setLabel(requiresPermissionsDesc.button());
                buttonData.setApi(api);
                itemData.getChildren().add(buttonData);
            }
        }
        return root;
    }

    /**
     * 权限列表处理
     *
     * @param context      应用上下文
     * @param basicPackage 基础包
     * @return 返回列表
     */
    @SuppressWarnings("rawtypes")
    public static List<Permission> permissionList(ApplicationContext context, String basicPackage) {
        Map<String, Object> map = context.getBeansWithAnnotation(Controller.class);
        if (CheckEmptyUtil.isEmpty(map)){
            map = context.getBeansWithAnnotation(RestController.class);
        }
        List<Permission> permissions = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object bean = entry.getValue();
            if (!StringUtils.contains(ClassUtils.getPackageName(bean.getClass()), basicPackage)) {
                continue;
            }

            Class<?> clz = bean.getClass();
            Class controllerClz = clz.getSuperclass();
            RequestMapping clazzRequestMapping = AnnotationUtils.findAnnotation(controllerClz, RequestMapping.class);
            List<Method> methods = MethodUtils.getMethodsListWithAnnotation(controllerClz, RequiresPermissions.class);
            for (Method method : methods) {
                RequiresPermissions requiresPermissions = AnnotationUtils.getAnnotation(method,
                        RequiresPermissions.class);
                RequiresPermissionsDesc requiresPermissionsDesc = AnnotationUtils.getAnnotation(method,
                        RequiresPermissionsDesc.class);

                if (requiresPermissions == null || requiresPermissionsDesc == null) {
                    continue;
                }

                String api = "";
                if (clazzRequestMapping != null) {
                    api = clazzRequestMapping.value()[0];
                }

                PostMapping postMapping = AnnotationUtils.getAnnotation(method, PostMapping.class);
                if (postMapping != null) {
                    api = "POST " + api + postMapping.value()[0];

                    Permission permission = new Permission();
                    permission.setRequiresPermissions(requiresPermissions);
                    permission.setRequiresPermissionsDesc(requiresPermissionsDesc);
                    permission.setApi(api);
                    permissions.add(permission);
                    continue;
                }
                GetMapping getMapping = AnnotationUtils.getAnnotation(method, GetMapping.class);
                if (getMapping != null) {
                    api = "GET " + api + getMapping.value()[0];
                    Permission permission = new Permission();
                    permission.setRequiresPermissions(requiresPermissions);
                    permission.setRequiresPermissionsDesc(requiresPermissionsDesc);
                    permission.setApi(api);
                    permissions.add(permission);
                    continue;
                }
                // TODO
                // 这里只支持GetMapping注解或者PostMapping注解，应该进一步提供灵活性
                throw new BusinessException("目前权限管理应该在method的前面使用GetMapping注解或者PostMapping注解");
            }
        }
        return permissions;
    }

    /**
     * 系统权限集合
     *
     * @param permissions 权限列表
     * @return 返回值
     */
    public static Set<String> permissionSet(List<Permission> permissions) {
        Set<String> permissionsSet = Sets.newConcurrentHashSet();
        for (Permission permission : permissions) {
            permissionsSet.add(permission.getRequiresPermissions().value()[0]);
        }
        return permissionsSet;
    }
}
