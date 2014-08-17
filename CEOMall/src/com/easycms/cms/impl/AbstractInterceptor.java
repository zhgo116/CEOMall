package com.easycms.cms.impl;

import javax.annotation.PostConstruct;



import com.easycms.cms.IInterceptor;
import com.easycms.cms.utils.CmsAppUtils;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.interceptor.CmsAppInterceptorManager;

public abstract class AbstractInterceptor implements IInterceptor {

	@PostConstruct
	public void initInterceptor() {
		Log4jUtils.info("InitInterceptor");
		CmsAppUtils.getBean(CmsAppInterceptorManager.class).putInterceptor(this.getClass());
	}

}
