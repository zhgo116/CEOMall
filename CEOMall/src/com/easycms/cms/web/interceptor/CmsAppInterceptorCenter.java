package com.easycms.cms.web.interceptor;

import java.util.LinkedList;

import com.easycms.cms.IInterceptor;

public class CmsAppInterceptorCenter {
	private static CmsAppInterceptorCenter appInterceptorCenter;
	private LinkedList<String> interceptors;

	public static CmsAppInterceptorCenter defaultCenter() {
		return appInterceptorCenter == null ? (appInterceptorCenter = new CmsAppInterceptorCenter()) : appInterceptorCenter;
	}

	private CmsAppInterceptorCenter() {
		interceptors = new LinkedList<String>();
	}

	public <T extends IInterceptor> void putInterceptor(Class<T> cls) {
		String name = cls.getSimpleName();
		name = String.format("%s%s", Character.toLowerCase(name.charAt(0)), name.substring(1));
		this.interceptors.add(name);
	}

	public String getInterceptor(int index) {
		return (index < 0 || index >= this.interceptors.size()) ? null : this.interceptors.get(index);
	}

	public void clear() {
		this.interceptors = new LinkedList<String>();
	}
}
