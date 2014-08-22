package com.ceomall.module.good;

import com.easycms.cms.IModule;
import com.easycms.cms.annotation.Module;

@Module(basePackage = "com.ceomall.module.good", state = 2)
public class GoodModule implements IModule {

	@Override
	public boolean install() {
		return true;
	}

	@Override
	public boolean unInstall() {
		return true;
	}

}
