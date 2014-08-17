package com.easycms.cms.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.easycms.cms.utils.CmsAppUtils;
import com.easycms.cms.web.dao.IOptionsDao;
import com.easycms.cms.web.utils.CmsWebUtils;

@Configuration
public class CmsAppListener implements ApplicationListener<ContextRefreshedEvent> {
	private static int count = 0;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if(++count > 1){
			CmsWebUtils.getServletContext().setAttribute("THEME", CmsWebUtils.getBean(IOptionsDao.class).query("theme"));
		}else{
			CmsAppUtils.flushContext();
		}
	}
}
