package com.huokangtou.server.controller.core;

import com.huokangtou.server.constent.Config;
import com.huokangtou.server.constent.UserRole;
import com.huokangtou.server.util.DateUtil;
import com.huokangtou.server.util.VolecityUIUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author 510655387@qq.com
 */
public class AdminRoleInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AdminRoleInterceptor.class);
    private static final String defaultUrl = "/forbidden";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String url = request.getRequestURI();
        boolean isOk = true;
        if (session.getAttribute(Config.USER_ROLE) != null) {
            String userRole = (String) session.getAttribute(Config.USER_ROLE);
            isOk = userRole.contains(UserRole.ADMIN.getKey());
        } else {
            isOk = false;
        }
        if (!isOk) {
            logger.warn("requst = {} 会话过期或者异常访问", request.toString());
            ModelAndView mv = new ModelAndView(defaultUrl);
            mv.addObject("forward", url);
            throw new ModelAndViewDefiningException(mv);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        if (modelAndView != null) {
            if (session.getAttribute(Config.USER_ROLE) != null) {
                String userRole = (String) session.getAttribute(Config.USER_ROLE);
                String userAccount = (String) session.getAttribute(Config.USER_ACCOUNT);
                if (userRole.contains(UserRole.COMMON.getKey())) {
                    modelAndView.addObject(UserRole.COMMON.getKey(), UserRole.COMMON.getKey());
                    modelAndView.addObject("userAccount", userAccount);
                }
                if (userRole.contains(UserRole.ADMIN.getKey())) {
                    modelAndView.addObject(UserRole.ADMIN.getKey(), UserRole.ADMIN.getKey());
                    modelAndView.addObject("userAccount", userAccount);
                }
            }
            modelAndView.addObject("DateUtil", DateUtil.getInstance());
            modelAndView.addObject("VolecityUIUtil", VolecityUIUtil.getInstance());
        }
    }

}
