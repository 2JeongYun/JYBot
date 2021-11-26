package com.neukrang.jybot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static ObjectMapper om = new ObjectMapper();

    public static <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            return om.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertJsonToObject(String json, TypeReference<T> typeReference) {
        try {
            return om.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            try {
                return (T) om.readValue(json, new TypeReference<ApiResponse<String>>() {});
            } catch (JsonProcessingException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public static String convertObjectToJson(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
