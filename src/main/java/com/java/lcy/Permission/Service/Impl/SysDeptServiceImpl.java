package com.java.lcy.Permission.Service.Impl;

import com.google.common.base.Preconditions;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Entity.SysDept;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Exception.ParamException;
import com.java.lcy.Permission.Mapper.SysDeptMapper;
import com.java.lcy.Permission.Mapper.SysLogMapper;
import com.java.lcy.Permission.Mapper.SysUserMapper;
import com.java.lcy.Permission.Param.DeptParam;
import com.java.lcy.Permission.Service.SysDeptService;
import com.java.lcy.Permission.Service.SysLogService;
import com.java.lcy.Permission.Util.BeanValidator;
import com.java.lcy.Permission.Util.IpUtil;
import com.java.lcy.Permission.Util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysDeptServiceImpl implements SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysLogService sysLogService;

    @Transactional
    @Override
    public void save(DeptParam deptParam) {
        BeanValidator.check(deptParam);
        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            log.error("【新增部门】 新增部门失败 同一层级下部门重复 deptParm={}", deptParam);
            throw new ParamException(ResultEnum.DEPT_REPEATION);
        }
        SysDept sysDept = new SysDept();
        BeanUtils.copyProperties(deptParam, sysDept);
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        sysDept.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysDept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysDept.setOperateTime(new Date());
        sysDeptMapper.insertSelective(sysDept);
        sysLogService.saveDeptLog(null,sysDept);
    }

    @Transactional
    @Override
    public void update(DeptParam deptParam) {
        BeanValidator.check(deptParam);
        if (checkExist(deptParam.getParentId(), deptParam.getName(), deptParam.getId())) {
            log.error("【更新部门】 更新部门失败 同一层级下部门重复 deptParm={}", deptParam);
            throw new ParamException(ResultEnum.DEPT_REPEATION);
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptParam.getId());
        Preconditions.checkNotNull(before, ResultEnum.UP_DEPT_NOT_EXIST.getMessage());
        SysDept after = new SysDept();
        BeanUtils.copyProperties(deptParam, after);
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptParam.getParentId()), deptParam.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before, after);
        sysLogService.saveDeptLog(before,after);
    }

    @Transactional
    void updateWithChild(SysDept before, SysDept after) {
        String oldLevelPrefix = before.getLevel();
        String newLevelPrefix = after.getLevel();
        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SysDept> oldSysDeptList = sysDeptMapper.getChildDeptListByLevel(oldLevelPrefix);
            if (CollectionUtils.isNotEmpty(oldSysDeptList)) {
                for (SysDept sysDept : oldSysDeptList) {
                    String level = sysDept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        sysDept.setLevel(level);
                    }
                }
                sysDeptMapper.batchUpdateLevel(oldSysDeptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }

    private boolean checkExist(Integer parentId, String deptName, Integer deptId) {
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;

    }

    private String getLevel(Integer deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null) {
            log.warn("【获取部门等级】 获取部门等级失败 当前部门不存在 deptId={}", deptId);
            return null;
        }
        return dept.getLevel();
    }

    @Transactional
    @Override
    public void delete(int deptId) {
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, ResultEnum.DELETE_DEPT_NOT_EXIST.getMessage());
        if (sysDeptMapper.countByParentId(deptId) > 0) {
            log.error("【删除部门】 删除部门失败 当前部门下面有子部门，无法删除 deptId={}", deptId);
            throw new ParamException(ResultEnum.CURRENT_DEPT_HAS_SUBDEPTS);//"当前部门下面有子部门，无法删除"
        }
        if (sysUserMapper.countByDeptId(deptId) > 0) {
            log.error("【删除部门】 删除部门失败 当前部门下面有用户，无法删除 deptId={}", deptId);
            throw new ParamException(ResultEnum.CURRENT_DEPT_HAS_USERS);//当前部门下面有用户，无法删除
        }
        sysDeptMapper.deleteByPrimaryKey(deptId);
    }


}
