package com.java.lcy.Permission.Controller;

import com.java.lcy.Permission.Common.JsonData;
import com.java.lcy.Permission.Dto.DeptLevelDto;
import com.java.lcy.Permission.Param.DeptParam;
import com.java.lcy.Permission.Service.SysDeptService;
import com.java.lcy.Permission.Service.Impl.SysTreeServiceImpl;
import com.java.lcy.Permission.Service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sys/dept")
@Slf4j
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreeService sysTreeService;

    @PostMapping("/save")
    @ResponseBody
    public JsonData saveDept(DeptParam deptParam){
     sysDeptService.save(deptParam);
     return JsonData.success();
    }

    @RequestMapping("/tree")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> deptLevelDtoList = sysTreeService.deptTree();
        return JsonData.success(deptLevelDtoList);
    }

    @PostMapping("/update")
    @ResponseBody
    public JsonData updateDept(DeptParam param) {
        sysDeptService.update(param);
        return JsonData.success();
    }
    @RequestMapping("/dept")
    public ModelAndView deptPage() {
        return new ModelAndView("dept");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JsonData deleteDept(@RequestParam("id")int id){
        sysDeptService.delete(id);
        return JsonData.success();
    }
}
