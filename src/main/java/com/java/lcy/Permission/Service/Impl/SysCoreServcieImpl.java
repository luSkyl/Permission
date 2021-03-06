package com.java.lcy.Permission.Service.Impl;

import com.google.common.collect.Lists;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Entity.SysAcl;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Enum.CacheKeyEnum;
import com.java.lcy.Permission.Mapper.SysAclMapper;
import com.java.lcy.Permission.Mapper.SysRoleAclMapper;
import com.java.lcy.Permission.Mapper.SysRoleUserMapper;
import com.java.lcy.Permission.Service.SysCacheService;
import com.java.lcy.Permission.Service.SysCoreServcie;
import com.java.lcy.Permission.Util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SysCoreServcieImpl implements SysCoreServcie {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysCacheService sysCacheService;

    @Override
    public List<SysAcl> getCurrentUserAclList() {
        Integer userId = RequestHolder.getCurrentUser().getId();
        return getUserAclList(userId);
    }

    @Override
    public List<SysAcl> getRoleAclList(int roleId) {
        List<Integer> userAclIdList = getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(userAclIdList)) {
            log.warn("【查找权限】 根据roleId查找权限列表失败 权限列表为空  roleId={}", roleId);
            return Lists.newArrayList();
        }
        return sysAclMapper.getByIdList(userAclIdList);
    }

    @Override
    public boolean hasUrlAcl(String url) {
        if (isSuperAdmin()) {
            return true;
        }
        List<SysAcl> aclList = sysAclMapper.getByUrl(url);
        if (CollectionUtils.isEmpty(aclList)) {
            return true;
        }

        List<SysAcl> userAclList = getCurrentUserAclListFromCache();
        Set<Integer> userAclIdSet = userAclList.stream().map(acl -> acl.getId()).collect(Collectors.toSet());

        boolean hasValidAcl = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SysAcl acl : aclList) {
            // 判断一个用户是否具有某个权限点的访问权限
            if (acl == null || acl.getStatus() != 1) { // 权限点无效
                continue;
            }
            hasValidAcl = true;
            if (userAclIdSet.contains(acl.getId())) {
                return true;
            }
        }
        if (!hasValidAcl) {
            return true;
        }
        return false;
    }

    @Override
    public List<SysAcl> getCurrentUserAclListFromCache() {
       int userId = RequestHolder.getCurrentUser().getId();
        String cacheValue = sysCacheService.getFromCache(CacheKeyEnum.USER_ACLS, String.valueOf(userId));
        if (StringUtils.isBlank(cacheValue)) {
            List<SysAcl> aclList = getCurrentUserAclList();
            if (CollectionUtils.isNotEmpty(aclList)) {
                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyEnum.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SysAcl>>() {
        });

    }

    @Override
    public List<SysAcl> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.getAll();
        }
        List<Integer> userRoleIdList = sysRoleUserMapper.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            log.warn("【查找角色】根据UserId查找角色列表失败 角色列表为空 userId={}", userId);
            return Lists.newArrayList();
        }
        List<Integer> userAclIdList = getAclIdListByRoleIdList(userRoleIdList);
        return sysAclMapper.getByIdList(userAclIdList);
    }

    private boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }

    private List<Integer> getAclIdListByRoleIdList(List<Integer> userRoleIdList) {
        List<Integer> userAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(userRoleIdList);
        if (CollectionUtils.isEmpty(userAclIdList)) {
            log.warn("【查找权限】根据RoleIdList查找权限列表失败 权限列表为空 userRoleIdList={}", userRoleIdList);
            return Lists.newArrayList();
        }
        return userAclIdList;
    }
}
