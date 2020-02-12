package com.java.lcy.Permission.Filter;

import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Constant.UserConstant;
import com.java.lcy.Permission.Entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "loginFilter",urlPatterns = {"/sys/*","/admin/*"})
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

       // SysUser sysUser = (SysUser)req.getSession().getAttribute(UserConstant.USER);
        SysUser sysUser= RequestHolder.getCurrentUser();
        if (sysUser == null) {
            log.warn("【登录拦截】 登录拦截成功 当前用户未登录 sysUser:{}",sysUser);
            resp.sendRedirect("/"+UserConstant.SIGNINPATH);
            return;
        }
     /*   RequestHolder.add(sysUser);
        RequestHolder.add(req);*/
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {

    }
}
