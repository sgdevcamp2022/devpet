package com.devpet.feed.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String getHash(String str) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte data : bytes) {
                sb.append(Integer.toString((data & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }
}
