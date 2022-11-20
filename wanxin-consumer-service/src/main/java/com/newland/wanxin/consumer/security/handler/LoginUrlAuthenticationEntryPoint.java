package com.newland.wanxin.consumer.security.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class LoginUrlAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public LoginUrlAuthenticationEntryPoint() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,PUT,POST,DELETE");
        response.addHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        JSONObject json=new JSONObject();
        json.put("code",0);
        json.put("msg","请登陆!");
        out.write(JSONObject.toJSONString(json));
        out.flush();
        out.close();
    }

}
