package com.easycms.cms.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.easycms.cms.annotation.Module;
import com.easycms.cms.notify.CmsNotifyCenter;

@Configuration
@ComponentScan(basePackages = "/", useDefaultFilters = false, includeFilters = @Filter(type = FilterType.ANNOTATION, value = Module.class))
public class CmsAppListener implements ApplicationListener<ContextRefreshedEvent> {
	private static int count = 0;

	@Autowired
	public void setApplicationContext(AnnotationConfigWebApplicationContext applicationContext) {
		CmsAppConfiguration.DefaultCmsApp().setApplicationContext(applicationContext);
	}


	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		CmsNotifyCenter.defaultNotifyCenter().postNotify("flushContext", count++);
	}
}
