package com.java.lcy.Permission.Common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JsonData<T>{

    private boolean res;
    private String msg;
    private T data;

    public JsonData(boolean res) {
        this.res = res;
    }

    public static JsonData success(Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.setData(object);
        return jsonData;
    }
    public static JsonData success(String msg,Object object){
        JsonData jsonData = new JsonData(true);
        jsonData.setMsg(msg);
        jsonData.setData(object);
        return jsonData;
    }

    public static JsonData success(){
        return new JsonData(true);

    }

    public static JsonData fail(String msg){
        JsonData jsonData = new JsonData(false);
        jsonData.setMsg(msg);
        return jsonData;
    }

    public Map<String,Object> toMap(){
        Map<String,Object> result = new HashMap<>();
        result.put("res",res);
        result.put("msg",msg);
        result.put("data",data);
        return result;
    }
}
