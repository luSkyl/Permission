package com.java.lcy.Permission.Exception;

import com.java.lcy.Permission.Enum.ResultEnum;

public class ParamException extends RuntimeException {
    private Integer code;

    public ParamException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ParamException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
