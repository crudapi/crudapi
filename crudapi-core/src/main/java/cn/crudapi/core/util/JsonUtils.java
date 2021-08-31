package cn.crudapi.core.util;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return JSON_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
        	System.err.println(e);
            return null;
        }
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        try {
            return JSON_MAPPER.readValue(json, valueType);
        } catch (Exception e) {
        	System.err.println(e);
            return null;
        }
    }
    
    public static <T> T toObject(String json, TypeReference<T> toValueTypeRef) {
        try {
            return JSON_MAPPER.readValue(json, toValueTypeRef);
        } catch (Exception e) {
        	System.err.println(e);
            return null;
        }
    }

    public static <T> Map<String, T> toMap(JsonNode jsonNode) {
        try {
            return JSON_MAPPER.convertValue(jsonNode, new TypeReference<Map<String, T>>() {
            });
        } catch (Exception e) {
        	System.err.println(e);
            return null;
        }
    }
    
    public static <T> List<Map<String, T>> toMapList(JsonNode jsonNode) {
        try {
            return JSON_MAPPER.convertValue(jsonNode, new TypeReference<List<Map<String, T>>>() {
            });
        } catch (Exception e) {
        	System.err.println(e);
            return null;
        }
    }

    public static <T> JsonNode toJsonNode(Map<String, T> map) {
        try {
            return JSON_MAPPER.convertValue(map, JsonNode.class);
        } catch (Exception e) {
        	System.err.println(e);
            return null;
        }
    }
}
