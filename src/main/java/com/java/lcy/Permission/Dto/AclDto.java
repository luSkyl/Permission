package com.java.lcy.Permission.Dto;

import com.java.lcy.Permission.Entity.SysAcl;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class AclDto extends SysAcl {

    //是否选中
    private boolean checked = false;

    //是否有权限
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl sysAcl){
        AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(sysAcl,aclDto);
        return aclDto;
    }
}
