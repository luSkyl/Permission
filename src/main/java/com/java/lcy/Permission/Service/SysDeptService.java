package com.java.lcy.Permission.Service;


import com.java.lcy.Permission.Param.DeptParam;

public interface SysDeptService {
    public void save( DeptParam deptParam);
    public void update(DeptParam deptParam);
    public void delete(int deptId);
}
