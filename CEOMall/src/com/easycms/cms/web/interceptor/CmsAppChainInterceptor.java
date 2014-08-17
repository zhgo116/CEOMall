package com.easycms.cms.web.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easycms.cms.IInterceptor;
import com.easycms.cms.utils.CmsAppUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

public class CmsAppChainInterceptor {
	
	private int index = 0;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private FilterChain chain;
	
	public CmsAppChainInterceptor(ServletRequest request,ServletResponse response,FilterChain chain) {
		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		this.chain = chain;
		CmsWebUtils.setRequest(this.request);
		CmsWebUtils.setResponse(this.response);
	}
	
	public void doInterceptor() throws IOException, ServletException {
		String beanName = CmsAppUtils.getBean(CmsAppInterceptorManager.class).getInterceptor(index++);
		if(beanName == null){
			this.chain.doFilter(request, response);
			CmsWebUtils.setRequest(null);
			CmsWebUtils.setResponse(null);
		}
		else 
			CmsAppUtils.getBean(beanName, IInterceptor.class).doFilter(request, response, this);
	}
}
