package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysRoleUser;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysRoleUserMapperTest {

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Test
    void deleteByPrimaryKey() {
        sysRoleUserMapper.deleteByPrimaryKey(18);
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setRoleId(3);
        sysRoleUser.setUserId(2);
        sysRoleUserMapper.insertSelective(sysRoleUser);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
        SysRoleUser sysRoleUser = sysRoleUserMapper.selectByPrimaryKey(18);
        sysRoleUser.setUserId(3);
        sysRoleUserMapper.updateByPrimaryKeySelective(sysRoleUser);
    }

    @Test
    void updateByPrimaryKey() {
    }
}