package com.easycms.cms.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Module;
import com.easycms.cms.utils.CmsAppUtils;

@Configuration
@ComponentScan(basePackages="/",useDefaultFilters=false,includeFilters=@Filter(type=FilterType.ANNOTATION,value=Module.class))
public class CmsAppConfiguration {

	private static CmsAppConfiguration cmsAppConfiguration;
	private AnnotationConfigWebApplicationContext applicationContext;
	private JSONObject jsonObject;
	
	public CmsAppConfiguration() {
		cmsAppConfiguration = this;
	}
	
	public static CmsAppConfiguration DefaultCmsApp() {
		return cmsAppConfiguration;
	}
	
	public AnnotationConfigWebApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Autowired
	public void setApplicationContext(
			AnnotationConfigWebApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}


	public JSONObject getJsonObject() {
		return jsonObject;
	}
	
	public JSONObject getModules() {
		JSONObject json = jsonObject.getJSONObject("Modules");
		if(json == null){
			json = new JSONObject();
			jsonObject.put("Modules", json);
		}
		return json;
	}
	
	public JSONObject getConfiguration() {
		JSONObject json = jsonObject.getJSONObject("Configuration");
		if(json == null){
			json = new JSONObject();
			jsonObject.put("Modules", json);
		}
		return json;
	}
	
	
	@PostConstruct
	public void loadConfiguration() {
		this.jsonObject = CmsAppUtils.loadCache();
	}
}
