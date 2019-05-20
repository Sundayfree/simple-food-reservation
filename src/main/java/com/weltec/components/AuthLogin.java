package com.weltec.components;


import com.google.common.base.Objects;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.weltec.pojo.User;
import com.weltec.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SoundbankResource;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-03 18:40
 */
@Component
public class AuthLogin implements HandlerInterceptor {

    @Autowired
    IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("currentUser");
        if(Objects.equal (null,user)){
            request.setAttribute("errorMsg","You have to Login first");
            request.getRequestDispatcher("/index.html").forward(request,response);
            return false;
        }else{
            User u= userService.findByID (user.getUserID ());
            request.getSession ().setAttribute ("currentUser",u);
        }
        return true;
    }
}
