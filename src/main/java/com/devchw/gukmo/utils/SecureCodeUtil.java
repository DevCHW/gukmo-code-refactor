package com.devchw.gukmo.utils;

public class SecureCodeUtil {
    // 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성
    public static String secureCode(String str) {
        str = str.replaceAll("<script", "&lt;script");
        return str;
    }
}
