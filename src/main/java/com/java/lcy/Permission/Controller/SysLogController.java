package com.java.lcy.Permission.Controller;

import com.java.lcy.Permission.Common.JsonData;
import com.java.lcy.Permission.Param.PageQuery;
import com.java.lcy.Permission.Param.SearchLogParam;
import com.java.lcy.Permission.Service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys/log")
public class SysLogController {
    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/log")
    public ModelAndView page() {
        return new ModelAndView("log");
    }

    @RequestMapping("/recover")
    @ResponseBody
    public JsonData recover(@RequestParam("id") int id) {
        sysLogService.recover(id);
        return JsonData.success();
    }

    @RequestMapping("/page")
    @ResponseBody
    public JsonData searchPage(SearchLogParam param, PageQuery page) {
        return JsonData.success(sysLogService.searchPageList(param, page));
    }
}
