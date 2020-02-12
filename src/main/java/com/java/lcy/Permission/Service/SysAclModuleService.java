package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Param.AclModuleParam;

public interface SysAclModuleService {
    public void save(AclModuleParam param);

    public void update(AclModuleParam param);

    public void delete(int aclModuleId);
}
