package ru.ivmiit.web.utils;

import javax.xml.bind.DatatypeConverter;
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


    public static String getBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
}
