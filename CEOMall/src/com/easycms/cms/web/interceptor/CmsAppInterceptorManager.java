package com.easycms.cms.web.interceptor;

import java.util.LinkedList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.easycms.cms.IInterceptor;
import com.easycms.cms.annotation.Module;

@Module
@Service
@Scope("singleton")
public class CmsAppInterceptorManager {
	private LinkedList<String> interceptors;
	
	public CmsAppInterceptorManager() {
		interceptors = new LinkedList<String>();
	}
	
	public <T extends IInterceptor> void putInterceptor(Class<T> cls) {
		String name = cls.getSimpleName();
		name = String.format("%s%s", Character.toLowerCase(name.charAt(0)),name.substring(1));
		this.interceptors.add(name);
	}
	
	public String getInterceptor(int index) {
		return (index < 0 || index >= this.interceptors.size())?null:this.interceptors.get(index);
	}
}
