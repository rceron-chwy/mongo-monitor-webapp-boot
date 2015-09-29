package com.mongodash.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mongodash.model.Server;
import com.mongodash.monitor.AgentKeysHolder;

@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	AgentKeysHolder keyHolder;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//Check for agentKey when call is made to API endpoints
		if(request.getRequestURL().toString().contains("/api/")) {
			String agentKey = request.getHeader("MongoDash-AgentKey");
			if(!keyHolder.isValidKey(agentKey)) {
				response.setStatus(HttpStatus.SC_FORBIDDEN);
				return false;
			}
			else {
				Server server = keyHolder.getServer(agentKey);
				request.setAttribute("server", server);				
			}
		}
		
		return super.preHandle(request, response, handler);
	}
	
}
