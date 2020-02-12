package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.PageResult;
import com.java.lcy.Permission.Param.UserParam;

import java.util.List;

public interface SysUserService {

    public void save(UserParam param);

    public void update(UserParam param);

    public SysUser findByKeyword(String keyword);

    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page);

    public List<SysUser> getAll();
}
