package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysAcl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysAclMapperTest {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Test
    void deleteByPrimaryKey() {
        int i = sysAclMapper.deleteByPrimaryKey(12);
        Assert.assertNotNull(i);

    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
        SysAcl sysAcl = new SysAcl();
        sysAcl.setCode("20171015212626_16");
        sysAclMapper.insertSelective(sysAcl);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
        SysAcl sysAcl = sysAclMapper.selectByPrimaryKey(12);
        sysAcl.setCode("20171015212626_18");
        sysAclMapper.updateByPrimaryKeySelective(sysAcl);
    }

    @Test
    void updateByPrimaryKey() {
    }
}