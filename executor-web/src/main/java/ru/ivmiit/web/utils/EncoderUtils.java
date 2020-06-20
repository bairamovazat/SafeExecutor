package ru.ivmiit.web.utils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncoderUtils {

    public static String getMd5LowerCase(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("MD5 instance not found", e);
        }
        md.update(data.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }

    public static String getMd5LowerCase(byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("MD5 instance not found", e);
        }
        md.update(data);
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }

    public static String encodeBase64(String data) {
        return Base64.getUrlEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] encodeBase64(byte[] data) {
        return Base64.getUrlEncoder().encode(data);
    }

    public static String decodeBase64(String data) {
        return new String(Base64.getUrlDecoder().decode(data.getBytes()));
    }
}
