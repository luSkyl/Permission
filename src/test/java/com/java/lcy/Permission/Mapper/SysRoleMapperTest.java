package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysRole;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    void deleteByPrimaryKey() {
        sysRoleMapper.deleteByPrimaryKey(6);
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
        SysRole sysRole = new SysRole();
        sysRole.setName("123");
        sysRoleMapper.insertSelective(sysRole);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(6);
        sysRole.setName("456");
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Test
    void updateByPrimaryKey() {
    }
}