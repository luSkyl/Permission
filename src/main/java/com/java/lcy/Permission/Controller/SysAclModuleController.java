package com.java.lcy.Permission.Controller;


import com.java.lcy.Permission.Common.JsonData;
import com.java.lcy.Permission.Entity.SysAclModule;
import com.java.lcy.Permission.Param.AclModuleParam;
import com.java.lcy.Permission.Service.SysAclModuleService;
import com.java.lcy.Permission.Service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService sysAclModuleService;
    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping("/save")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param) {
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    @PostMapping("/update")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param) {
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    @RequestMapping("/acl")
    public ModelAndView aclPage() {
        return new ModelAndView("acl");
    }

    @RequestMapping("/tree")
    @ResponseBody
    public JsonData tree() {
        return JsonData.success(sysTreeService.aclModuleTree());
    }
}
