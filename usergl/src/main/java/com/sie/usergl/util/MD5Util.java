package com.sie.usergl.util;

import java.security.MessageDigest;

public class MD5Util {
    private static final String SALT = "Sie_User_rights_management";

    public static String encode(String password) {
        password = password + SALT;
        MessageDigest md5 ;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = md5Byte & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            System.out.println(a[i] ^ 't');
            a[i] = (char) (a[i] ^ 't');
            System.out.println(a[i]);
        }
        String s = new String(a);
        return s;

    }



}
