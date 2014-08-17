package com.easycms.cms.web.utils;

import org.springframework.beans.BeanUtils;

import com.easycms.cms.utils.Log4jUtils;


public class CmsBeanUtils extends BeanUtils{
	
	public static Class<?> findClass(String cls) {
		if(cls == null || cls.isEmpty())return null;
		try {
			return Class.forName(cls);
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return  null;
	}
	
	
	
}
