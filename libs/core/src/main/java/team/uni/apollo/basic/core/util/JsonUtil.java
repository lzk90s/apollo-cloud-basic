package team.uni.apollo.basic.core.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class JsonUtil {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(FORMAT_PATTERN));
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getInstance() {
        return OBJECT_MAPPER;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {
        if (null == obj) {
            return "";
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> json2list(String jsonStr, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonStr)) {
            return Collections.emptyList();
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, getCollectionType(ArrayList.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static <T> T json2pojo(String jsonStr, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json string convert to map
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> json2map(String jsonStr) {
        if (StringUtils.isEmpty(jsonStr)) {
            return Collections.emptyMap();
        }
        try {
            return OBJECT_MAPPER.readValue(jsonStr, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * map convert to javaBean
     */
    public static <T> T map2pojo(Map<?, ?> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    public static void main(String[] args) {
        String json = "[{\"id\":\"1\",\"name\":\"li\"}]";
        var list = json2list(json, TestObj.class);
        System.out.println(list);
    }

    public static class TestObj {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
