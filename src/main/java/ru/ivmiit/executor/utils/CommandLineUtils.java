package ru.ivmiit.executor.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandLineUtils {

    public static Map<String, String> getAllEjudgeCommand(String[] args) {
        Map<String, String> result = new HashMap<>();
        Arrays.stream(args).forEach(arg -> {
            // -- -> 2
            // min 1 key symbol -> 1
            // = -> 1
            // min 1 value symbol -> 1
            // total -> 5
            if (arg.length() > 5 && arg.substring(0, 2).equals("--")
                    && arg.contains("=")) {
                String key = arg.split("=")[0].replace("--", "");
                String value = arg.split("=")[1];
                if (key.length() > 0 && value.length() > 0) {
                    result.put(key, value);
                }
            }
        });

        return result;
    }
}