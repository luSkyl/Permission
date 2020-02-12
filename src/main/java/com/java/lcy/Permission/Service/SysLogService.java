package com.java.lcy.Permission.Service;

import com.java.lcy.Permission.Entity.*;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.PageResult;
import com.java.lcy.Permission.Param.SearchLogParam;

public interface SysLogService {
    public void recover(int id);
    public PageResult<SysLogWithBLOBs> searchPageList(SearchLogParam param, PageQuery page) ;
    public void saveDeptLog(SysDept before, SysDept after);
    public void saveUserLog(SysUser before, SysUser after);
    public void saveAclModuleLog(SysAclModule before, SysAclModule after);
    public void saveAclLog(SysAcl before, SysAcl after);
    public void saveRoleLog(SysRole before, SysRole after);
}
