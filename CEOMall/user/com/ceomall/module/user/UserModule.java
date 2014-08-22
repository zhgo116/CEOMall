package com.ceomall.module.user;

import com.easycms.cms.IModule;
import com.easycms.cms.annotation.Module;

@Module(basePackage="com.ceomall.module.user",state=2)
public class UserModule implements IModule{

	@Override
	public boolean install() {
		return true;
	}

	@Override
	public boolean unInstall() {
		return true;
	}

}
