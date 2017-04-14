package com.holy.yangsheng.utils;

import java.util.Random;

/**
 * Created by Hailin on 2017/3/2.
 */

public class StringUtils {
    /**
     * 生成随机字符串
     * @param length 表示生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
