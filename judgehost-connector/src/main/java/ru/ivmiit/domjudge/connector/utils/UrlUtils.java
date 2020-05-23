package ru.ivmiit.domjudge.connector.utils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlUtils {
    public static Map<String, String> splitQueryParameter(String query) {
        Map<String, String> keyValue = new HashMap<>();
        if(query == null) {
            return keyValue;
        }
         Arrays.stream(query.split("&"))
                .forEach(p -> {
                    String[] keyValueData = p.split("=");
                    if(keyValueData.length == 1) {
                        keyValue.put(keyValueData[0], null);
                    } else if(keyValueData.length > 1){
                        keyValue.put(keyValueData[0], keyValueData[1]);
                    }
                });

        return keyValue;
    }
}
