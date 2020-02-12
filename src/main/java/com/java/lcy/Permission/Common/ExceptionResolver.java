package com.java.lcy.Permission.Common;

import com.java.lcy.Permission.Exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionResolver {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public JsonData paramExceptionHandler(HttpServletRequest httpServletRequest, ParamException e) {
        return JsonData.fail(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public JsonData nullPointerExceptionHandler(HttpServletRequest httpServletRequest, NullPointerException e) {
        return JsonData.fail(e.getMessage());
    }


}
