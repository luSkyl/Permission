package com.java.lcy.Permission.Service.Impl;

import com.google.common.base.Preconditions;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Entity.SysDept;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Exception.ParamException;
import com.java.lcy.Permission.Mapper.SysUserMapper;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.PageResult;
import com.java.lcy.Permission.Param.UserParam;
import com.java.lcy.Permission.Service.SysLogService;
import com.java.lcy.Permission.Service.SysUserService;
import com.java.lcy.Permission.Util.BeanValidator;
import com.java.lcy.Permission.Util.IpUtil;
import com.java.lcy.Permission.Util.MD5Util;
import com.java.lcy.Permission.Util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysLogService sysLogService;



    @Transactional
    @Override
    public void save(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())) {
            log.error("【保存用户】 保存用户失败 电话已被占用 param={}", param);
            throw new ParamException(ResultEnum.TELEPHONE_REPEAT);
        }
        if (checkEmailExist(param.getMail(), param.getId())) {
            log.error("【保存用户】 保存用户失败 邮箱已被占用 param={}", param);
            throw new ParamException(ResultEnum.EMAIL_REPEAT);
        }
        String password = PasswordUtil.randomPassword();
        //TODO:
        password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(param, sysUser);
        sysUser.setPassword(encryptedPassword);
        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());
        //TODO: send email
        sysUserMapper.insertSelective(sysUser);
        sysLogService.saveUserLog(null,sysUser);
    }

    @Transactional
    @Override
    public void update(UserParam param) {
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(), param.getId())){
            log.error("【更新用户】 更新用户失败 电话已被占用 param={}", param);
            throw new ParamException(ResultEnum.TELEPHONE_REPEAT);
        }
        if (checkEmailExist(param.getMail(), param.getId())){
            log.error("【更新用户】 更新用户失败 邮箱已被占用 param={}", param);
            throw new ParamException(ResultEnum.EMAIL_REPEAT);
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, ResultEnum.UP_DEPT_NOT_EXIST.getMessage());
        SysUser after = new SysUser();
        BeanUtils.copyProperties(param, after);
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        sysUserMapper.updateByPrimaryKeySelective(after);
        sysLogService.saveUserLog(before,after);
    }

    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery page) {
        BeanValidator.check(page);
        int count = sysUserMapper.countByDeptId(deptId);
        if(count > 0){
            List<SysUser> pageList = sysUserMapper.getPageByDeptId(deptId, page);
            return PageResult.<SysUser>builder().total(count).data(pageList).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }
}
