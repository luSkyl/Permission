package com.java.lcy.Permission.Controller;


import com.google.common.collect.Maps;
import com.java.lcy.Permission.Common.JsonData;
import com.java.lcy.Permission.Entity.SysRole;
import com.java.lcy.Permission.Param.AclParam;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Service.SysAclService;
import com.java.lcy.Permission.Service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/save")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/page")
    @ResponseBody
    public JsonData page(@RequestParam("aclModuleId")Integer aclModuleId, PageQuery pageQuery){
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId,pageQuery));
    }

    @RequestMapping("/acls")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId")int aclId){
        Map<String,Object> map = Maps.newHashMap();
        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);
        map.put("users",sysRoleService.getUserListByRoleList(roleList));
        map.put("roles",roleList);
        return JsonData.success(map);
    }


}
