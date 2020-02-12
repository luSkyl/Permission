package com.java.lcy.Permission.Param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageQuery {

    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Min(value = 1,message = "每页展现的数据不合法")
    private int pageSize = 10;

    private int offset;

    public int getOffset() {
        return (pageNo-1)*pageSize;
    }
}
