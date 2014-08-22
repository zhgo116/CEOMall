package com.easycms.cms.notify;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easycms.cms.annotation.Notify;
import com.easycms.cms.utils.Assert;
import com.easycms.cms.utils.ReflationUtils;
import com.easycms.cms.utils.RegexUtils;

public class CmsNotifyCenter {
	private static CmsNotifyCenter cmsNotifyCenter;

	private Map<String, List<Method>> notices;

	public static CmsNotifyCenter defaultNotifyCenter() {
		return cmsNotifyCenter == null ? (cmsNotifyCenter = new CmsNotifyCenter()) : cmsNotifyCenter;
	}

	private CmsNotifyCenter() {
		this.notices = Collections.synchronizedMap(new HashMap<String, List<Method>>());
	}

	public void postNotify(String notice, Object object) {
		if (Assert.isEmpty(notice) || this.notices.isEmpty() || !this.notices.containsKey(notice))
			return;
		List<Method> list = this.notices.get(notice);
		Method[] methods = new Method[list.size()];
		list.toArray(methods);
		for (Method method : methods) {
			Object target = ReflationUtils.newInstance(method.getDeclaringClass());
			int len = 0;
			if (null != target && (len = Array.getLength(method.getParameterTypes())) < 2) {
				object = len > 0 ? ReflationUtils.invoke(target, method, object) : ReflationUtils.invoke(target, method);
			}
		}
	}

	public void postNotify(String notice) {
		this.postNotify(notice, null);
	}

	public boolean addNotifyListener(Class<?> clazz) {
		if (null == clazz)
			return false;
		Method[] methods = ReflationUtils.getMethods(clazz);
		if (null == methods || Array.getLength(methods) <= 0)
			return false;
		for (Method method : methods) {
			Notify anno = method.getAnnotation(Notify.class);
			if (null != anno && Assert.notEmpty(anno.value()) && Array.getLength(method.getParameterTypes()) < 2)
				addNotifyListener(anno.value(), method);
		}
		return true;
	}

	public boolean addNotifyListener(String notice, Method method) {
		if (Assert.isNull(notice, method) || Assert.isEmpty(notice))
			return false;
		List<Method> list = this.notices.get(notice);
		if (null == list)
			this.notices.put(notice, (list = Collections.synchronizedList(new ArrayList<Method>())));
		if (!list.contains(method))
			list.add(method);
		return true;
	}

	public void clear() {
		this.notices.clear();
	}

	public void removeNotifyListener(String notice) {
		if (Assert.notEmpty(notice))
			this.notices.remove(notice);
	}

	public void removeNotifyListener(String notice, Method method) {
		if (Assert.isNull(notice, method) || Assert.isEmpty(notice) || !this.notices.containsKey(notice))
			return;
		List<Method> list = this.notices.get(notice);
		if (list.contains(method))
			list.remove(method);
	}

	public void removeNotifyListener(Class<?> clazz, String pattern) {
		if (Assert.isNull(clazz, pattern) || Assert.isEmpty(pattern))
			return;
		Method[] methods = ReflationUtils.getMethods(clazz);
		if (null == methods || Array.getLength(methods) <= 0)
			return;
		for (Method method : methods) {
			Notify anno = method.getAnnotation(Notify.class);
			if (null != anno && Assert.notEmpty(anno.value()) && RegexUtils.isMatch(pattern, method.getName()) && Array.getLength(method.getParameterTypes()) < 2)
				removeNotifyListener(anno.value(), method);
		}
	}

	public void removeNotifyListener(Class<?> clazz) {
		removeNotifyListener(clazz, ".*+");
	}

}
