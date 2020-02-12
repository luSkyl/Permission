package com.java.lcy.Permission.Util;


import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;

@Slf4j
public class JsonMapper {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        //config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);//设置在反序列化时忽略在JSON字符串中存在，而在Java中不存在的属性
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);//如果是空对象的时候,不抛异常
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);//属性为null的转换
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    }



    /**
     * 对象转String
     * @param src
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T src){
        if (null == src){
            return null;
        }
        try {
            return src instanceof String ? (String) src:objectMapper.writeValueAsString(src);
        } catch (Exception e) {
            log.warn("【对象转换成String】 转换异常 e={}",e);
            return null;
        }
    }

    public static <T> T string2Obj(String src, TypeReference<T> typeReference){
        if(null == src || null == typeReference){
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? src : objectMapper.readValue(src, typeReference));
        } catch (Exception e){
            log.warn("【String转换成对象】 转换异常 e={}",e);
            return null;
        }
    }
}
