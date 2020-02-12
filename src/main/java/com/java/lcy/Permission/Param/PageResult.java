package com.java.lcy.Permission.Param;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageResult<T> {
    private List<T> data = Lists.newArrayList();
    private int total;

}
