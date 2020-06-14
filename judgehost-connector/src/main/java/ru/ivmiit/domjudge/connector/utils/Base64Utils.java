package ru.ivmiit.domjudge.connector.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {

    public static String decode(String data) {
        if(data == null) {
            return null;
        }
        byte[] decodedBytes = Base64.getUrlDecoder().decode(data);
        return new String(decodedBytes);
    }

    public static String encode(String data) {
        if(data == null) {
            return null;
        }
        return Base64.getUrlEncoder().encodeToString(data.getBytes());
    }

    public static String decodeFromUrl(String url) {
        if(url == null) {
            return null;
        }
        try {
            return decode(URLDecoder.decode(url, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
