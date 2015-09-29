package com.mongodash.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

public class ModelInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		String context = request.getContextPath().replaceAll("/", "");
		String baseURL = getBaseURL(request, context);

		if (modelAndView != null) {

			boolean isViewObject = modelAndView.getView() == null;

			boolean isRedirectView = false; 
			if (modelAndView.getViewName() == null || modelAndView.getViewName().startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX))
				isRedirectView = true;

			if (modelAndView.hasView() && (isViewObject && !isRedirectView))
				addBaseModelData(request, modelAndView, context, baseURL);
			
		}
	}

	private String getBaseURL(HttpServletRequest request, String context) {
		StringBuilder baseURL = new StringBuilder();
		baseURL.append(request.getScheme()).append("://").append(request.getServerName());

		int port = request.getServerPort();
		if (port != 80)
			baseURL.append(":").append(port);

		if (!StringUtils.isEmpty(context))
			baseURL.append("/").append(context);

		return baseURL.toString();
	}

	private void addBaseModelData(HttpServletRequest request, ModelAndView modelAndView, String context, String baseURL) {
		modelAndView.addObject("SERVER_NAME", request.getServerName());
		modelAndView.addObject("SCHEME", request.getScheme());
		modelAndView.addObject("REQUEST_URL", request.getRequestURL());
		modelAndView.addObject("CONTEXT", context);
		modelAndView.addObject("BASE_URL", baseURL);
	}
}
