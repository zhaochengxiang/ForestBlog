package com.zcx.blog.interceptor;


import com.zcx.blog.entity.Result;
import com.zcx.blog.entity.Session;
import com.zcx.blog.service.SessionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    private String cookieName = "xid";
    @Autowired
    private SessionService sessionService;
    private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private List<Pattern> freeList = new ArrayList<>();

    {
        freeList.add(Pattern.compile("/user/login"));
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String url = urlPathHelper.getLookupPathForRequest(request);

        String xid = getXid(request, response);
        // 生成当前session
        Session session = sessionService.bind(xid);

        for (Pattern pattern : freeList) {
            if (pattern.matcher(url).find()) {
                return true;
            }
        }

        String token = request.getHeader("Access-Token");

        if (token == null) {
            response.getWriter().write(Result.of(417,"请求头中缺少 Access-Token"));
            return false;
        }

        String username = session.getUsername();
        if (username == null) {
            response.getWriter().write(Result.of(401,"令牌无效，请求重新验证身份"));
            return false;
        }

        return true;
    }

    private String getXid(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    String id = cookie.getValue();
                    if (!id.isEmpty()) {
                        return id;
                    }
                }
            }
        }

        String id = sessionService.getId();
        Cookie cookie = new Cookie(cookieName, id);
        cookie.setPath("/");
        response.addCookie(cookie);
        return id;
    }

}
