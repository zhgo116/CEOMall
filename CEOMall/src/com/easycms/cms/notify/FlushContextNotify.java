package com.easycms.cms.notify;

import java.util.Map;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.easycms.cms.IInterceptor;
import com.easycms.cms.IModule;
import com.easycms.cms.annotation.Interceptor;
import com.easycms.cms.annotation.Module;
import com.easycms.cms.annotation.Notify;
import com.easycms.cms.annotation.Tag;
import com.easycms.cms.utils.CmsAppUtils;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.interceptor.CmsAppInterceptorCenter;

import freemarker.template.TemplateModelException;

public class FlushContextNotify {

	@Notify("flushContext")
	public void flushContext(int time) {
		if (time == 0) {
			Log4jUtils.info("The first load context, start scanning module!");
			// 加载模块
			Map<String, Object> beans = CmsAppUtils.cmsApp().getApplicationContext().getBeansWithAnnotation(Module.class);
			if (null != beans && !beans.isEmpty()) {
				for (Object obj : beans.values()) {
					CmsAppUtils.rigsterModule(((IModule) obj).getClass());
				}
				CmsAppUtils.flushCache();
			}
			
			Log4jUtils.info("The scanning module is completed, start loading module!");
			CmsAppUtils.flushContext();
		} else if (time > 0) {
			Log4jUtils.info("Multiple load context, start loading interceptor!");
			// 加载拦截器
			Map<String, Object> beans = CmsAppUtils.cmsApp().getApplicationContext().getBeansWithAnnotation(Interceptor.class);
			if (null != beans && !beans.isEmpty()) {
				for (Object obj : beans.values()) {
					CmsAppInterceptorCenter.defaultCenter().putInterceptor(((IInterceptor) obj).getClass());
				}
			}
			// 加载TAG
			beans = CmsAppUtils.cmsApp().getApplicationContext().getBeansWithAnnotation(Tag.class);
			if (null != beans && !beans.isEmpty()) {
				for (Object obj : beans.values()) {
					Tag anno = obj.getClass().getAnnotation(Tag.class);
					try {
						CmsAppUtils.getBean(FreeMarkerConfigurer.class).getConfiguration().setSharedVariable(anno.value(), obj);
					} catch (TemplateModelException e) {
						Log4jUtils.error(e.getMessage());
					}
				}
			}
			CmsNotifyCenter.defaultNotifyCenter().postNotify("initCMS");
		}
	}
}
