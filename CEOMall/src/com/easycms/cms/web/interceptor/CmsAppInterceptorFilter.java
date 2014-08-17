package com.easycms.cms.web.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CmsAppInterceptorFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		new CmsAppChainInterceptor(arg0, arg1, arg2).doInterceptor();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}