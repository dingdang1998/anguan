package com.oket.ccic.util.base;

/**
 * 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 *
 * @author admin
 */
public class Constants {
    /**
     * session中存放用户信息的key值
     */
    public static final String SESSION_USERDTO_INFO = "UserBean";
    /**
     * 超级管理员ID
     */
    public static final int ADMIN_ID = 1;
    /**
     * 普通管理员ID
     */
    public static final int N_ADMIN_ID = 4;
    /**
     * 外部管理员ID
     */
    public static final int OUTER_ADMIN_ID = 2;
    /**
     * 内部管理员ID
     */
    public static final int INNER_ADMIN_ID = 3;
    /**
     * 游客角色ID
     */
    public static final int VISITOR_ID = 7;
    /**
     * 验证码数组
     */
    public static final String[] LETTERS = new String[]{
            "q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m",
            "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    /**
     * 获取验证码
     *
     * @return
     */
    public static String getCode() {
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            codeBuilder.append(Constants.LETTERS[(int) Math.floor(Math.random() * Constants.LETTERS.length)]);
        }
        return codeBuilder.toString();
    }
}
