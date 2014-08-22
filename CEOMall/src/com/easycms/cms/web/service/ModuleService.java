package com.easycms.cms.web.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.utils.CmsAppUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

@Service
public class ModuleService {

	public JSONObject list() {
		return CmsAppUtils.cmsApp().getModules();
	}

	public void changeModule() {
		JSONObject json = CmsWebUtils.request2Json();
		CmsWebUtils.changeModuleState(json.getString("name"), json.getIntValue("state"));
		CmsWebUtils.flushContext();
	}
}
