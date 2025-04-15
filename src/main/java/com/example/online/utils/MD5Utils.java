package com.example.campus.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */


public class MD5Utils {
    // 加盐方法
    private static final String SALT = "campus_salt" ;
    /*public static String getSalt(String info) {
        return SALT + info ;
    }*/
    public static String md5Encoder(String pass, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            md.update(salt.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
