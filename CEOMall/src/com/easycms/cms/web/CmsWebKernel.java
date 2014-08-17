package com.easycms.cms.web;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.easycms.cms.annotation.Kernel;
import com.easycms.cms.utils.Log4jUtils;

@Kernel
@Service
@ComponentScan("com.easycms.cms.web")
public class CmsWebKernel{

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("/");
		configurer.setDefaultEncoding("utf-8");
		Properties props = new Properties();
		try {
			props.load(CmsWebKernel.class.getResourceAsStream("/res/freemarker.properties"));
			System.out.println(props);
		} catch (IOException e) {
			Log4jUtils.error(e.getMessage());
		}
		configurer.setFreemarkerSettings(props);
		return configurer;
	}
	
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setViewClass(FreeMarkerView.class);
		viewResolver.setContentType("text/html;charset=utf-8");
		viewResolver.setExposeRequestAttributes(true);
		viewResolver.setExposeSessionAttributes(true);
		viewResolver.setExposeSpringMacroHelpers(true);
		return viewResolver;
	}
	
}
