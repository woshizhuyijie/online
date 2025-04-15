package com.example.campus.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * * @author woshizhuyijie
 * @date 2024-12-22
 *
 * */

public class UsernameGenerator {

    // 根据输入的字符串生成唯一用户名
    public static String generateUsername(String input) throws NoSuchAlgorithmException {
        // 使用 MD5 或 SHA-256 哈希算法
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes());

        // 转换为十六进制字符串并截取前 10 位
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.substring(0, 10);  // 截取前 10 位
    }

   /* public static void main(String[] args) throws NoSuchAlgorithmException {
        String input = "user@example.com";  // 作为示例使用的输入
        System.out.println("Generated Username: " + generateUsername(input));
    }*/
}
