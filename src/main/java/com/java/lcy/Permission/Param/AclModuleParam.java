package com.java.lcy.Permission.Param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AclModuleParam {
    private Integer id;

    @NotBlank(message = "权限模块名称不可以为空")
    @Length(min = 2, max = 20, message = "权限模块长度需要在2~20个字之间")
    private String name;

    private Integer parentId;

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer seq;

    @NotNull(message = "权限模块名不可以为空")
    @Min(value = 0,message = "权限模块状态不合法")
    @Max(value = 2,message = "权限模块状态不合法")
    private Integer status;

    @Length(max = 200, message = "权限模块备注需要在200个字之内")
    private String remark;
}
