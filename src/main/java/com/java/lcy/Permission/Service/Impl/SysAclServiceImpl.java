package com.java.lcy.Permission.Service.Impl;

import com.google.common.base.Preconditions;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Entity.SysAcl;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Exception.ParamException;
import com.java.lcy.Permission.Mapper.SysAclMapper;
import com.java.lcy.Permission.Param.AclParam;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.PageResult;
import com.java.lcy.Permission.Service.SysAclService;
import com.java.lcy.Permission.Service.SysLogService;
import com.java.lcy.Permission.Util.BeanValidator;
import com.java.lcy.Permission.Util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysAclServiceImpl implements SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysLogService sysLogService;

    @Transactional
    @Override
    public void save(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            log.error("【保存权限】 保存权限失败 当前权限模块下面存在相同名称的权限点 param={}", param);
            throw new ParamException(ResultEnum.ACL_REPEATION);//"当前权限模块下面存在相同名称的权限点"
        }
        SysAcl acl = new SysAcl();
        BeanUtils.copyProperties(param, acl);
        acl.setCode(generateCode());
        acl.setOperator(RequestHolder.getCurrentUser().getUsername());
        acl.setOperateTime(new Date());
        acl.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.insertSelective(acl);
        sysLogService.saveAclLog(null, acl);
    }

    @Transactional
    @Override
    public void update(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getAclModuleId(), param.getName(), param.getId())) {
            log.error("【更新权限】 更新权限失败 当前权限模块下面存在相同名称的权限点 param={}", param);
            throw new ParamException(ResultEnum.ACL_REPEATION);//"当前权限模块下面存在相同名称的权限点"
        }
        SysAcl before = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, ResultEnum.UPDATE_ACL_NOT_EXIST);//"待更新的权限点不存在"

        SysAcl after = new SysAcl();
        BeanUtils.copyProperties(param, after);
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateTime(new Date());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysAclMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveAclLog(before, after);
    }

    @Override
    public PageResult<SysAcl> getPageByAclModuleId(int aclModuleId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysAclMapper.countByAclModuleId(aclModuleId);
        if (count > 0) {
            List<SysAcl> aclList = sysAclMapper.getPageByAclModuleId(aclModuleId, page);
            return PageResult.<SysAcl>builder().data(aclList).total(count).build();
        }
        return PageResult.<SysAcl>builder().build();
    }

    private boolean checkExist(int aclModuleId, String name, Integer id) {
        return sysAclMapper.countByNameAndAclModuleId(aclModuleId, name, id) > 0;
    }

    private String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }
}
