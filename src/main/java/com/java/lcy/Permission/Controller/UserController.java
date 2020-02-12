package com.java.lcy.Permission.Controller;

import com.java.lcy.Permission.Constant.UserConstant;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Enum.ResultEnum;
import com.java.lcy.Permission.Service.SysUserService;
import com.java.lcy.Permission.Util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter(UserConstant.USERNAME);
        String password = request.getParameter(UserConstant.PASSWORD);

        SysUser sysUser = sysUserService.findByKeyword(username);
        String errorMsg = "";
        String res = request.getParameter("res");

        if (StringUtils.isBlank(username)) {
            errorMsg = ResultEnum.USERNAME_CANNOT_BE_EMPTY.getMessage();
        } else if (StringUtils.isBlank(password)) {
            errorMsg = ResultEnum.PASSWORD_CANNOT_BE_EMPTY.getMessage();
        } else if (sysUser == null) {
            errorMsg = ResultEnum.USER_NOT_EXIST.getMessage();
        } else if (!sysUser.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = ResultEnum.USERNAME_OR_PASSWORD_ERROR.getMessage();
        } else if (sysUser.getStatus() != 1) {
            errorMsg = ResultEnum.USER_BE_FROZEN.getMessage();
        } else {
            // login success
            request.getSession().setAttribute(UserConstant.USER, sysUser);
            if (StringUtils.isNotBlank(res)) {
                response.sendRedirect(res);
                return;
            } else {
                response.sendRedirect("/admin/index"); //TODO
                return;
            }
        }

        request.setAttribute(UserConstant.ERROR, errorMsg);
        request.setAttribute(UserConstant.USERNAME, username);
        if (StringUtils.isNotBlank(res)) {
            request.setAttribute(UserConstant.RESULT, res);
        }
        request.getRequestDispatcher(UserConstant.SIGNINPATH).forward(request, response);
        return;
    }


    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect(UserConstant.LOGINPATH);
    }
}
