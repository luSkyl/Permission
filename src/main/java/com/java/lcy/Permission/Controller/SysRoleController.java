package com.java.lcy.Permission.Controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.java.lcy.Permission.Common.JsonData;
import com.java.lcy.Permission.Dto.AclModuleLevelDto;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Mapper.SysRoleMapper;
import com.java.lcy.Permission.Param.RoleParam;
import com.java.lcy.Permission.Service.*;
import com.java.lcy.Permission.Util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysRoleAclService sysRoleAclService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/save")
    @ResponseBody
    public JsonData saveRole(RoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/list")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    @RequestMapping("/role")
    public ModelAndView role() {
        return new ModelAndView("role");
    }

    @PostMapping("/roleTree")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") Integer roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    @PostMapping("/changeAcls")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }

    @PostMapping("/users")
    @ResponseBody
    public JsonData users(@RequestParam("roleId") int roleId) {
        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
        List<SysUser> allUserList = sysUserService.getAll();
        List<SysUser> unSelectedUserList = Lists.newArrayList();
        Set<Integer> selectedUserSet = selectedUserList.stream().map(e -> e.getId()).collect(Collectors.toSet());
        for (SysUser sysUser : allUserList) {
            if (sysUser.getStatus() == 1 && !selectedUserSet.contains(sysUser.getId())) {
                unSelectedUserList.add(sysUser);
            }
        }
        Map<String, List<SysUser>> map = Maps.newHashMap();
        map.put("selected", selectedUserList);
        map.put("unselected", unSelectedUserList);

        return JsonData.success(map);

    }

    @PostMapping("/changeUsers")
    @ResponseBody
    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId,userIdList);
        return JsonData.success();
    }
}
