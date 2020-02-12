package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Dto.AclModuleLevelDto;
import com.java.lcy.Permission.Dto.DeptLevelDto;

import java.util.List;

public interface SysTreeService {
    public List<DeptLevelDto> deptTree();

    public List<AclModuleLevelDto> aclModuleTree();

    public List<AclModuleLevelDto> roleTree(int roleId);

    public List<AclModuleLevelDto> userAclTree(int userId);
}
