package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysLogWithBLOBs;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysLogMapperTest {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Test
    void deleteByPrimaryKey() {
        int i = sysLogMapper.deleteByPrimaryKey(26);
    }

    @Test
    void insert() {
    }

    @Test
    void insertSelective() {
        SysLogWithBLOBs sysLogWithBLOBs = new SysLogWithBLOBs();
        sysLogWithBLOBs.setStatus(3);
        sysLogWithBLOBs.setTargetId(5);
      sysLogMapper.insertSelective(sysLogWithBLOBs);
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
        SysLogWithBLOBs sysLogWithBLOBs = sysLogMapper.selectByPrimaryKey(26);
        sysLogWithBLOBs.setTargetId(6);
        sysLogMapper.updateByPrimaryKeySelective(sysLogWithBLOBs);
    }

    @Test
    void updateByPrimaryKeyWithBLOBs() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}