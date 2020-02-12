package com.java.lcy.Permission.Filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.java.lcy.Permission.Common.ApplicationContextHelper;
import com.java.lcy.Permission.Common.RequestHolder;
import com.java.lcy.Permission.Constant.UserConstant;
import com.java.lcy.Permission.Entity.SysUser;
import com.java.lcy.Permission.Service.SysCoreServcie;
import com.java.lcy.Permission.Util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
@WebFilter(filterName = "aclControlFilter",urlPatterns = {"/sys/*","/admin/*"},
        initParams = {@WebInitParam(name = "exclusionUrls",value = "/sys/user/noAuth,/login"),
                       @WebInitParam(name = "targetFilterLifecycle",value = "true")})
public class AclControlFilter implements Filter {


    private static Set<String> exclusionUrlSet = Sets.newHashSet();
    private static final String noAuthUrl = "/sys/user/noAuth";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exclusionUrls = filterConfig.getInitParameter("exclusionUrls");
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newHashSet(exclusionUrlList);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map requestMap = request.getParameterMap();
        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        SysUser sysUser = (SysUser)request.getSession().getAttribute(UserConstant.USER);
        if (sysUser == null) {
            log.warn("【用户拦截】 用户未登录 路径:{} 参数:{}", servletPath, JsonMapper.obj2String(requestMap));
            noAuth(request, response);
            return;
        }
        RequestHolder.add(sysUser);
        RequestHolder.add(request);
        SysCoreServcie sysCoreService = ApplicationContextHelper.popBean(SysCoreServcie.class);
        if (!sysCoreService.hasUrlAcl(servletPath)) {
            log.warn("【用户拦截】 用户没有权限  用户={}  路径={}, 参数={}", JsonMapper.obj2String(sysUser), servletPath, JsonMapper.obj2String(requestMap));
            noAuth(request, response);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    private void noAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
            clientRedirect(noAuthUrl, response);
            return;
    }

    private void clientRedirect(String url, HttpServletResponse response) throws IOException{
        response.setHeader("Content-Type", "text/html");
        response.getWriter().print("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n" + "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>\n"
                + "<title>跳转中...</title>\n" + "</head>\n" + "<body>\n" + "跳转中，请稍候...\n" + "<script type=\"text/javascript\">//<![CDATA[\n"
                + "window.location.href='" + url + "?ret='+encodeURIComponent(window.location.href);\n" + "//]]></script>\n" + "</body>\n" + "</html>\n");
    }


    @Override
    public void destroy() {

    }
}
