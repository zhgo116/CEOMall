package com.easycms.cms;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easycms.cms.web.interceptor.CmsAppChainInterceptor;


public interface IInterceptor {
	public void doFilter(HttpServletRequest	request, HttpServletResponse response,
			CmsAppChainInterceptor chain) throws IOException, ServletException;
}
