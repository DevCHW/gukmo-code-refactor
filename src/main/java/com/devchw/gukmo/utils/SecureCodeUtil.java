package com.devchw.gukmo.utils;

public class SecureCodeUtil {
    public static String secureCode(String str) {
        str = str.replace("&lt;p&gt;", "<p>");
        str = str.replace("&lt;/p&gt;", "</p>");
        str = str.replace("&amp;nbsp;", "&nbsp;");
        str = str.replace("&lt;br&gt;", "<br>");
        return str;
    }
}
