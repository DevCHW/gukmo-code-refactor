package com.devchw.gukmo.utils;

import java.util.Random;

public class MyUtil {
    /**
     * 난수 발생
     */
    public static String getAuthKey(int size) {
        Random random = new Random();

        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < size) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        return buffer.toString();
    }
}
