package com.java.lcy.Permission.Util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

@Slf4j
public class BeanValidator {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static Validator validator;

    public static <T> Map<String, String> validate(T t, Class... groups) {
        validator = validatorFactory.getValidator();
        Set validate = validator.validate(t, groups);
        if (validate.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap map = Maps.newLinkedHashMap();
            validate.stream().forEach(e -> map.put(((ConstraintViolation) e).getPropertyPath().toString(), ((ConstraintViolation) e).getMessage()));
            return map;
        }
    }

    public static Map<String, String> validate(Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Iterator<?> iterator = collection.iterator();
        Map map;
        do {
            if (!iterator.hasNext()) {
                return Collections.emptyMap();
            }
            map = validate(iterator.next(), new Class[0]);
        } while (map.isEmpty());
        return map;
    }

    public static Map<String, String> validateObject(Object first, Object... objects) {
        if (objects != null && objects.length > 0) {
            return validate(Arrays.asList(first, objects));
        } else {
            return validate(first, new Class[0]);
        }
    }

    public static void check(Object param) throws ParamException {
        Map<String, String> map = BeanValidator.validateObject(param);
        if (MapUtils.isNotEmpty(map)) {
            log.error("【参数异常】 Map为空 map={}", map);
            throw new ParamException(ResultEnum.PARAM_EXCEPTION);
        }
    }

    public static Validator getValidator() {
        return validator;
    }

}
