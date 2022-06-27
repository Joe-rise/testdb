package com.joe.testdb.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JacksonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    public JacksonUtils(@Autowired ObjectMapper om) {
        this.objectMapper = om;
    }
    public static String toJsonStrByJackson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            throw new RuntimeException(var2);
        }
    }
    public static <T> T convertToObject(String json, Class<T> valueType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }
    public static <T> T convertToObject(String json, TypeReference valueTypeRef) {
        try {
            return (T) objectMapper.readValue(json, valueTypeRef);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }
    public static <T> T convertToObject(Object fromValue, Class<T> valueType) {
        try {
            return objectMapper.convertValue(fromValue, valueType);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }
    public static boolean isJsonStr(String str) {
        try {
            JsonNode node = objectMapper.readTree(str);
            if(node.size()>0){
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    public static JsonNode getTree(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readTree(json);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }
}
