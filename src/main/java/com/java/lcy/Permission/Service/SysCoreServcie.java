package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Entity.SysAcl;

import java.util.List;

public interface SysCoreServcie {
    public List<SysAcl> getCurrentUserAclList();
    public List<SysAcl> getRoleAclList(int roleId);
    public boolean hasUrlAcl(String url);
    public List<SysAcl> getCurrentUserAclListFromCache();
    public List<SysAcl> getUserAclList(int userId);
}
