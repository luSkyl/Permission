package com.java.lcy.Permission.Mapper;

import com.java.lcy.Permission.Entity.SysDept;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SysDeptMapperTest {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Test
    void countByNameAndParentId() {
        int i = sysDeptMapper.countByNameAndParentId(0, "技术部", 1);
        System.out.println(i);
    }

    @Test
    void getChildDeptListByLevel() {
        List<SysDept> childDeptListByLevel = sysDeptMapper.getChildDeptListByLevel("0");
        System.out.println(childDeptListByLevel.toString());
    }
}