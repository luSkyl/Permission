package com.java.lcy.Permission.Dto;

import com.google.common.collect.Lists;
import com.java.lcy.Permission.Entity.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class DeptLevelDto extends SysDept {
    private List<DeptLevelDto> deptList = Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept sysDept){
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDept,deptLevelDto);
        return deptLevelDto;
    }
}
