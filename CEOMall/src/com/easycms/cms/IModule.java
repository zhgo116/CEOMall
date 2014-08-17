package com.easycms.cms;

public interface IModule {
	final int NOINSTALL = 0;
	final int INSTALLED = 1;
	final int ENABLED = 2;
	
	boolean install();
	
	boolean unInstall();
	
}
