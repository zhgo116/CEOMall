package com.easycms.cms.web.interceptor.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easycms.cms.IInterceptor;
import com.easycms.cms.annotation.Interceptor;
import com.easycms.cms.web.interceptor.CmsAppChainInterceptor;

@Interceptor
public class EncodingInterceptor implements IInterceptor {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, CmsAppChainInterceptor chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		chain.doInterceptor();
	}

}
