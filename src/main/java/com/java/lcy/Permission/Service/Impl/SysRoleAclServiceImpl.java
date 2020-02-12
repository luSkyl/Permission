package com.java.lcy.Permission.Service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Constant.LogType;
import com.java.lcy.Permission.Entity.SysLogWithBLOBs;
import com.java.lcy.Permission.Entity.SysRoleAcl;
import com.java.lcy.Permission.Mapper.SysLogMapper;
import com.java.lcy.Permission.Mapper.SysRoleAclMapper;
import com.java.lcy.Permission.Service.SysLogService;
import com.java.lcy.Permission.Service.SysRoleAclService;
import com.java.lcy.Permission.Util.IpUtil;
import com.java.lcy.Permission.Util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SysRoleAclServiceImpl implements SysRoleAclService {
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;
    @Resource
    private SysLogMapper sysLogMapper;

    @Transactional
    @Override
    public void changeRoleAcls(Integer roleId, List<Integer> aclIdList) {
        List<Integer> originAclIdList = sysRoleAclMapper.getAclIdListByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isNotEmpty(originAclIdList)) {
            if (originAclIdList.size() == aclIdList.size()) {
                Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
                Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
                originAclIdSet.removeAll(aclIdSet);
                if (CollectionUtils.isEmpty(originAclIdSet)) {
                    log.warn("【改变角色权限】 当前所选择的权限与之前权限一致 roleId={}, aclIdList={}",roleId,aclIdList);
                    return;
                }
            }
        }
        updateRoleAcls(roleId, aclIdList);
        saveRoleAclLog(roleId, originAclIdList, aclIdList);

    }

    @Transactional
    void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        sysRoleAclMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(aclIdList)) {
            log.warn("【更新角色权限】 更新角色权限失败 所要增加的权限列表为空 roleId={}",roleId);
            return;
        }
        List<SysRoleAcl> roleAclList = Lists.newArrayList();
        for (Integer aclId : aclIdList) {
            SysRoleAcl sysRoleAcl = SysRoleAcl.builder().roleId(roleId).aclId(aclId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
            roleAclList.add(sysRoleAcl);
        }
        sysRoleAclMapper.batchInsert(roleAclList);
    }

    @Transactional
    void saveRoleAclLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_ACL);
        sysLog.setTargetId(roleId);
        sysLog.setOldValue(before == null ? "" : JsonMapper.obj2String(before));
        sysLog.setNewValue(after == null ? "" : JsonMapper.obj2String(after));
        sysLog.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysLog.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysLog.setOperateTime(new Date());
        sysLog.setStatus(1);
        sysLogMapper.insertSelective(sysLog);
    }
}
