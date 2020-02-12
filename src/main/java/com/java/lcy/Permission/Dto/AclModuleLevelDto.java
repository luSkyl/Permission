package com.java.lcy.Permission.Dto;

import com.google.common.collect.Lists;
import com.java.lcy.Permission.Entity.SysAclModule;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class AclModuleLevelDto extends SysAclModule {
    private List<AclModuleLevelDto> aclModuleList = Lists.newArrayList();
    private List<AclDto> aclDtoList = Lists.newArrayList();

    public static AclModuleLevelDto adapt(SysAclModule sysAclModule) {
        AclModuleLevelDto aclModuleLevelDto = new AclModuleLevelDto();
        BeanUtils.copyProperties(sysAclModule, aclModuleLevelDto);
        return aclModuleLevelDto;
    }
}
