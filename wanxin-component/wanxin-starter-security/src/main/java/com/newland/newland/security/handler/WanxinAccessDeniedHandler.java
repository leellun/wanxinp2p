package com.newland.newland.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.newland.newland.security.enumeration.SecurityErrorCode;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WanxinAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request,
					   HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException{
		JSONObject json=new JSONObject();
		json.put("code", SecurityErrorCode.ACCESS_DENIED.getCode());
		json.put("msg",SecurityErrorCode.ACCESS_DENIED.getDesc());
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(JSONObject.toJSONString(json));
	}

}
 

