package com.oket.ccic.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @program: console_manage
 * @description:
 * @author: SunBiaoLong
 * @create: 2019-02-26 17:23
 **/
public class PasswordUtils {
    /**
     * 算法名
     */
    private final static String ALGORITHM_NAME = "md5";
    /**
     * 加密次数
     */
    private final static int HASH_ITERATIONS = 2;

    /**
     * 获取加密后的密码
     *
     * @param username
     * @param password
     * @return
     */
    public static String encryptPassword(String username, String password) {
        // 四个参数分别标识算法名称，散列对象，散列使用的salt值，散列次数。
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(username), HASH_ITERATIONS).toHex();
    }

    /**
     * 密码丢失以后计算密码
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(encryptPassword("noradmin2", "123456"));
    }
}
