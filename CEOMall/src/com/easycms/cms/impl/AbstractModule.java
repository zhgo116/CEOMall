package com.easycms.cms.impl;

import javax.annotation.PostConstruct;



import com.easycms.cms.IModule;
import com.easycms.cms.utils.CmsAppUtils;
import com.easycms.cms.utils.Log4jUtils;

public abstract class AbstractModule implements IModule {

	@PostConstruct
	public void initModule() {
		CmsAppUtils.rigsterModule(this.getClass());
	}

	@Override
	public boolean install() {
		Log4jUtils.info("Install");
		return true;
	}

	@Override
	public boolean unInstall() {
		Log4jUtils.info("UnInstall");
		return true;
	}

}
