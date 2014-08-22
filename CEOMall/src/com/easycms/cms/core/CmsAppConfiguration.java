package com.easycms.cms.core;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.notify.CmsNotifyCenter;
import com.easycms.cms.notify.FlushContextNotify;
import com.easycms.cms.utils.CmsAppUtils;

public class CmsAppConfiguration {

	private static CmsAppConfiguration cmsAppConfiguration;
	private AnnotationConfigWebApplicationContext applicationContext;
	private JSONObject jsonObject;

	private CmsAppConfiguration() {
		cmsAppConfiguration = this;
		this.loadConfiguration();
		CmsNotifyCenter.defaultNotifyCenter().addNotifyListener(FlushContextNotify.class);
	}

	public static CmsAppConfiguration DefaultCmsApp() {
		return cmsAppConfiguration == null ? (cmsAppConfiguration = new CmsAppConfiguration()) : cmsAppConfiguration;
	}

	public AnnotationConfigWebApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(AnnotationConfigWebApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public JSONObject getModules() {
		JSONObject json = jsonObject.getJSONObject("Modules");
		if (json == null) {
			json = new JSONObject();
			jsonObject.put("Modules", json);
		}
		return json;
	}

	public JSONObject getConfiguration() {
		JSONObject json = jsonObject.getJSONObject("Configuration");
		if (json == null) {
			json = new JSONObject();
			jsonObject.put("Configuration", json);
		}
		return json;
	}

	public void loadConfiguration() {
		this.jsonObject = CmsAppUtils.loadCache();
	}
}
