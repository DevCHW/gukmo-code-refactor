package com.devchw.gukmo.utils;

import java.util.Random;

public class NumberUtil {
    /**
     * 난수 발생시키기
     * @param 난수의 길이
     * @return 난수(문자열)
     */
    public static String createRandomNumber(int size) {
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
