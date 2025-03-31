package com.peteratef.caching_proxy.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public static String generateMD5HashForURI(String uri) {
        try {
            // MD5 hashing algorithm
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            // Compute the hash of the URI
            byte[] hashBytes = messageDigest.digest(uri.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the MD5 hash as a string
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle exception (e.g., MD5 algorithm not found)
            System.err.println("Error generating hash: " + e.getMessage());
            return null;
        }
    }
}
