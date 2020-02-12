package com.java.lcy.Permission.Service.Impl;

import com.google.common.base.Preconditions;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Entity.SysAclModule;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Exception.ParamException;
import com.java.lcy.Permission.Mapper.SysAclMapper;
import com.java.lcy.Permission.Mapper.SysAclModuleMapper;
import com.java.lcy.Permission.Param.AclModuleParam;
import com.java.lcy.Permission.Service.SysAclModuleService;
import com.java.lcy.Permission.Service.SysLogService;
import com.java.lcy.Permission.Util.BeanValidator;
import com.java.lcy.Permission.Util.IpUtil;
import com.java.lcy.Permission.Util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysAclModuleServiceImpl implements SysAclModuleService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;
    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysLogService sysLogService;

    @Transactional
    @Override
    public void save(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            log.warn("【保存权限】 保存权限失败 同一层级下存在相同名称的权限模块 param={}",param);
            throw new ParamException(ResultEnum.ACL_MOUDLE_REPEATION);//同一层级下存在相同名称的权限模块
        }
        SysAclModule aclModule = new SysAclModule();
        BeanUtils.copyProperties(param,aclModule);
        aclModule.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        aclModule.setOperator(RequestHolder.getCurrentUser().getUsername());
        aclModule.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        aclModule.setOperateTime(new Date());
        sysAclModuleMapper.insertSelective(aclModule);
       sysLogService.saveAclModuleLog(null, aclModule);
    }

    @Transactional
    @Override
    public void update(AclModuleParam param) {
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            log.warn("【更新权限】 更新权限失败 同一层级下存在相同名称的权限模块 param={}",param);
            throw new ParamException(ResultEnum.ACL_MOUDLE_REPEATION);//同一层级下存在相同名称的权限模块
        }
        SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, ResultEnum.UP_ACL_MODULE_NOT_EXIST.getMessage());//"待更新的权限模块不存在"

        SysAclModule after = new SysAclModule();
        BeanUtils.copyProperties(before,after);
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before, after);
        sysLogService.saveAclModuleLog(before, after);
    }

    @Transactional
    @Override
    public void delete(int aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        Preconditions.checkNotNull(aclModule, ResultEnum.DELETE_DEPT_NOT_EXIST.getMessage());//"待删除的权限模块不存在，无法删除"
        if(sysAclModuleMapper.countByParentId(aclModule.getId()) > 0) {
            log.error("【删除权限模块】 删除权限模块失败  当前模块下面有子模块，无法删除  aclModuleId={}",aclModuleId);
            throw new ParamException(ResultEnum.CURRENT_ACL_MODULE_HAS_SUBMODULES);//"当前模块下面有子模块，无法删除"
        }
        if (sysAclMapper.countByAclModuleId(aclModule.getId()) > 0) {
            log.error("【删除权限模块】 删除权限模块失败  当前模块下面有用户，无法删除  aclModuleId={}",aclModuleId);
            throw new ParamException(ResultEnum.CURRENT_MODULE_HAS_USERS);//当前模块下面有用户，无法删除
        }
        sysAclModuleMapper.deleteByPrimaryKey(aclModuleId);
    }
    @Transactional
    void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleList = sysAclModuleMapper.getChildAclModuleListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                for (SysAclModule aclModule : aclModuleList) {
                    String level = aclModule.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        aclModule.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(aclModuleList);
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    private boolean checkExist(Integer parentId, String aclModuleName, Integer deptId) {
        return sysAclModuleMapper.countByNameAndParentId(parentId, aclModuleName, deptId) > 0;
    }

    private String getLevel(Integer aclModuleId) {
        SysAclModule aclModule = sysAclModuleMapper.selectByPrimaryKey(aclModuleId);
        if (aclModule == null) {
            log.warn("【获取权限模块等级】 获取权限模块等级失败 当前权限模块不存在 aclModuleId={}",aclModuleId);
            return null;
        }
        return aclModule.getLevel();
    }

}
