package ru.ivmiit.domjudge.connector.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Utils {

    public static String decode(String data) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(data);
        return new String(decodedBytes);
    }

    public static String encode(String data) {
        return Base64.getUrlEncoder().encodeToString(data.getBytes());
    }

    public static String decodeFromUrl(String url) {
        try {
            return decode(URLDecoder.decode(url, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
