package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Entity.SysAcl;
import com.java.lcy.Permission.Param.AclParam;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.PageResult;

public interface SysAclService {
    public void save(AclParam param);

    public void update(AclParam param);

    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page);
}
