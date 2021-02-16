package com.demeter.cloud.core.constant;

/**
 * <p>封装Qicloud项目RegexConstant类.<br></p>
 * <p>//TODO...<br></p>
 *
 * @author Powered by marklin 2020-12-07 23:08
 * @version 1.0.0
 * <p>Copyright © 2018-2020 Pivotal Cloud Technology Systems Incorporated. All rights reserved.<br></p>
 */
public class RegexConstant {
    /** 精确验证:手机号 */
    public static final String PHONE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    /**
     * 普通验证:手机号只按照长度[11位]
     */
    public static final String PHONE_LENGTH = "^[1][3,4,5,7,8][0-9]{9}$";
    /** 校验15位身份证 */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /** 校验18位身份证 */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /** 校验正整数 */
    public static final String REGEX_POSITIVE = "^[0-9]*[1-9][0-9]*$";
    /** 校验地址信息等 */
    public static final String REGEX_ADDRESS =
            "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
}
