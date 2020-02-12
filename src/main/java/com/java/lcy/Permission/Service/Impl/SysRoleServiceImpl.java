package com.java.lcy.Permission.Service.Impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Entity.SysRole;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Exception.ParamException;
import com.java.lcy.Permission.Mapper.SysRoleAclMapper;
import com.java.lcy.Permission.Mapper.SysRoleMapper;
import com.java.lcy.Permission.Mapper.SysRoleUserMapper;
import com.java.lcy.Permission.Mapper.SysUserMapper;
import com.java.lcy.Permission.Param.RoleParam;
import com.java.lcy.Permission.Service.SysLogService;
import com.java.lcy.Permission.Service.SysRoleService;
import com.java.lcy.Permission.Util.BeanValidator;
import com.java.lcy.Permission.Util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogService sysLogService;

    @Transactional
    @Override
    public void save(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            log.error("【保存用户】 保存用户失败 角色名称已经存在 param={}", param);
            throw new ParamException(ResultEnum.ROLE_REPEATION);//"角色名称已经存在"
        }
        SysRole role = new SysRole();
        BeanUtils.copyProperties(param, role);
        role.setOperator(RequestHolder.getCurrentUser().getUsername());
        role.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        role.setOperateTime(new Date());
        sysRoleMapper.insertSelective(role);
        sysLogService.saveRoleLog(null, role);

    }

    @Transactional
    @Override
    public void update(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            log.error("【更新用户】 更新用户失败 角色名称已经存在 param={}", param);
            throw new ParamException(ResultEnum.ROLE_REPEATION);//"角色名称已经存在"
        }
        SysRole before = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, ResultEnum.UPDATE_ROLE_NOT_EXIST.getMessage());//"待更新的角色不存在"
        SysRole after = new SysRole();
        BeanUtils.copyProperties(param, after);
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysRoleMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveRoleLog(before, after);
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.getAll();
    }

    @Override
    public List<SysRole> getRoleListByUserId(int userId) {
        List<Integer> roleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            log.warn("【查找角色】 通过userId查询角色列表失败 userId={}", userId);
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    @Override
    public List<SysRole> getRoleListByAclId(int aclId) {
        List<Integer> roleIdList = sysRoleAclMapper.getRoleIdListByAclId(aclId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            log.warn("【查找角色】 通过aclId查询角色列表失败 aclId={}", aclId);
            return Lists.newArrayList();
        }
        return sysRoleMapper.getByIdList(roleIdList);
    }

    @Override
    public List<SysUser> getUserListByRoleList(List<SysRole> roleList) {
        if (CollectionUtils.isEmpty(roleList)) {
            return Lists.newArrayList();
        }
        List<Integer> roleIdList = roleList.stream().map(role -> role.getId()).collect(Collectors.toList());
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(userIdList)) {
            log.warn("【查找用户】 通过RoleList查找用户列表失败 roleList={}", roleIdList);
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    private boolean checkExist(String name, Integer id) {
        return sysRoleMapper.countByName(name, id) > 0;
    }
}
