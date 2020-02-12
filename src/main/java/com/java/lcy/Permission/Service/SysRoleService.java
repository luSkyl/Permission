package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Entity.SysRole;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Param.RoleParam;

import java.util.List;

public interface SysRoleService {
    public void save(RoleParam param);

    public void update(RoleParam param);

    public List<SysRole> getAll();

    public List<SysRole> getRoleListByUserId(int userId);

    public List<SysRole> getRoleListByAclId(int aclId);

    public List<SysUser> getUserListByRoleList(List<SysRole> roleList);

}
