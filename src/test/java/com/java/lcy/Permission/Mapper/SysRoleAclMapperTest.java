package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysRole;
import com.java.lcy.Permission.Entity.SysRoleAcl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysRoleAclMapperTest {

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Test
    void deleteByPrimaryKey() {
        sysRoleAclMapper.deleteByPrimaryKey(13);

    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
        SysRoleAcl sysRoleAcl = new SysRoleAcl();
        sysRoleAcl.setAclId(123);
        sysRoleAcl.setRoleId(123);
        sysRoleAclMapper.insertSelective(sysRoleAcl);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
        SysRoleAcl sysRoleAcl = sysRoleAclMapper.selectByPrimaryKey(13);
        sysRoleAcl.setRoleId(321);
        sysRoleAclMapper.updateByPrimaryKeySelective(sysRoleAcl);
    }

    @Test
    void updateByPrimaryKey() {
    }
}