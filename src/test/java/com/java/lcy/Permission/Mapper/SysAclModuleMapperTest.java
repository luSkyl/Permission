package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysAclModule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysAclModuleMapperTest {

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Test
    void deleteByPrimaryKey() {
        sysAclModuleMapper.deleteByPrimaryKey(12);
    }

    @Test
    void insert() {

    }

    @Test
    void insertSelective() {
        SysAclModule sysAclModule = new SysAclModule();
        sysAclModule.setName("权限更新记录管理");
        sysAclModule.setLevel("0.8");
        sysAclModule.setParentId(6);
        sysAclModuleMapper.insertSelective(sysAclModule);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
        SysAclModule sysAclModule = sysAclModuleMapper.selectByPrimaryKey(12);
        sysAclModule.setParentId(0);
        sysAclModuleMapper.updateByPrimaryKeySelective(sysAclModule);
    }

    @Test
    void updateByPrimaryKey() {
    }
}