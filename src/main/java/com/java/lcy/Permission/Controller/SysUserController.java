package com.java.lcy.Permission.Controller;

import com.google.common.collect.Maps;
import com.java.lcy.Permission.Common.JsonData;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.PageResult;
import com.java.lcy.Permission.Param.UserParam;
import com.java.lcy.Permission.Service.SysRoleService;
import com.java.lcy.Permission.Service.SysTreeService;
import com.java.lcy.Permission.Service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysTreeService sysTreeService;
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/save")
    @ResponseBody
    public JsonData saveUser(UserParam userParam) {
        sysUserService.save(userParam);
        return JsonData.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public JsonData updateDept(UserParam userParam) {
        sysUserService.update(userParam);
        return JsonData.success();
    }

    @RequestMapping("/page")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery) {
        return JsonData.success(sysUserService.getPageByDeptId(deptId, pageQuery));
    }

    @RequestMapping("/acls")
    @ResponseBody
    public JsonData acls(@RequestParam("userId")int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls",sysTreeService.userAclTree(userId));
        map.put("roles",sysRoleService.getRoleListByUserId(userId));
        return JsonData.success(map);
    }

    @RequestMapping("/noAuth")
    public ModelAndView noAuth(){
        return new ModelAndView("noAuth");
    }

}
