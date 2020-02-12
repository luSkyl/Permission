package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Entity.SysUser;

import java.util.List;

public interface SysRoleUserService {
    public List<SysUser> getListByRoleId(int roleId);
    public void changeRoleUsers(int roleId, List<Integer> userIdList);
}
