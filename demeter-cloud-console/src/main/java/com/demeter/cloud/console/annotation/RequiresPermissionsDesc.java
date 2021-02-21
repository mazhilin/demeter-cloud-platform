package com.demeter.cloud.console.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**权限表达描述
 * @author marklin
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermissionsDesc {
	/**
	 * 菜单-menu
	 *
	 * @return menu
	 */
	String[] menu();

	/**
	 * 菜单-button
	 *
	 * @return button
	 */
	String button();
}
