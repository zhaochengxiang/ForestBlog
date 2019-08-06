package com.zcx.blog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GsonUtils {
    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Double.class,
                (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                    BigDecimal value = BigDecimal.valueOf(src);
                    return new JsonPrimitive(value);
                });
        gson = gsonBuilder.create();
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static Map fromJson(String json) {
        return gson.fromJson(json, Map.class);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static Map toMap(Object object) {
        Map<String, Object> result = new HashMap<>();
        try {
            BeanInfo info = Introspector.getBeanInfo(object.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null) {
                    result.put(pd.getName(), reader.invoke(object));
                }
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
