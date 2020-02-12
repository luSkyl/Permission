package com.java.lcy.Permission.Service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Constant.LogType;
import com.java.lcy.Permission.Entity.SysLogWithBLOBs;
import com.java.lcy.Permission.Entity.SysRoleUser;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Mapper.SysLogMapper;
import com.java.lcy.Permission.Mapper.SysRoleUserMapper;
import com.java.lcy.Permission.Mapper.SysUserMapper;
import com.java.lcy.Permission.Service.SysRoleUserService;
import com.java.lcy.Permission.Util.IpUtil;
import com.java.lcy.Permission.Util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public List<SysUser> getListByRoleId(int roleId) {
        List<Integer> userIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            log.warn("【查找用户】 查找用户列表失败 用户列表为空 roleId={}",roleId);
            return Lists.newArrayList();
        }
        return sysUserMapper.getByIdList(userIdList);
    }

    @Transactional
    @Override
    public void changeRoleUsers(int roleId, List<Integer> userIdList) {
        List<Integer> originUserIdList = sysRoleUserMapper.getUserIdListByRoleId(roleId);
            if (originUserIdList.size() == userIdList.size()) {
                Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
                Set<Integer> userIdSet = Sets.newHashSet(userIdList);
                originUserIdSet.removeAll(userIdSet);
                if (CollectionUtils.isEmpty(originUserIdSet)) {
                    log.warn("【更改用户角色】 更改用户角色失败 当前用户角色不需要更改 roleId={}, userIdList={}",roleId,userIdList);
                    return;
                }
            }
        updateRoleUsers(roleId, userIdList);
        saveRoleUserLog(roleId, originUserIdList, userIdList);
    }

    @Transactional
    void updateRoleUsers(int roleId, List<Integer> userIdList) {
        sysRoleUserMapper.deleteByRoleId(roleId);
        if (CollectionUtils.isEmpty(userIdList)) {
            log.warn("【更改用户角色】 更改用户角色失败 当前用户列表为空 roleId={}, userIdList={}",roleId,userIdList);
            return;
        }
        List<SysRoleUser> roleUserList = Lists.newArrayList();
        for (Integer userId : userIdList) {
            SysRoleUser roleUser = SysRoleUser.builder().roleId(roleId).userId(userId).operator(RequestHolder.getCurrentUser().getUsername())
                    .operateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest())).operateTime(new Date()).build();
            roleUserList.add(roleUser);
        }
        sysRoleUserMapper.batchInsert(roleUserList);
    }

    @Transactional
    void saveRoleUserLog(int roleId, List<Integer> before, List<Integer> after) {
        SysLogWithBLOBs sysLog = new SysLogWithBLOBs();
        sysLog.setType(LogType.TYPE_ROLE_USER);
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
