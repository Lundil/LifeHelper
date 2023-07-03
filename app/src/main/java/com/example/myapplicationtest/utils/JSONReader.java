package com.example.myapplicationtest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONReader {

    public String getMessage(String json){
        Pattern messagePattern = Pattern.compile("\"message\"\\s*:\\s*\"([^,]*)\",");

        Matcher message_matcher = messagePattern.matcher(json);

        if (message_matcher.find()) {
            return message_matcher.group(1);
        }

        return null;
    }

    public String getAttribute(String json, String attribute){
        Pattern messagePattern = Pattern.compile("\"attribute\"\\s*:\\s*\"([^,]*)\",");

        Matcher message_matcher = messagePattern.matcher(json);

        if (message_matcher.find()) {
            return message_matcher.group(1);
        }

        return null;
    }
}
