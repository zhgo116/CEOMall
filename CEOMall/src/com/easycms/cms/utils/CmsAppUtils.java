package com.easycms.cms.utils;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.IModule;
import com.easycms.cms.annotation.Module;
import com.easycms.cms.core.CmsAppBeansScan;
import com.easycms.cms.core.CmsAppConfiguration;
import com.easycms.cms.core.CmsAppListener;

public class CmsAppUtils {

	public static CmsAppConfiguration cmsApp() {
		return CmsAppConfiguration.DefaultCmsApp();
	}

	public static JSONObject loadCache() {
		String path = String.format("%sres/cms.json", CmsAppUtils.class.getResource("/").getFile());
		try {
			return JSON.parseObject(FileUtils.readFileToString(new File(path)));
		} catch (Exception e) {
			Log4jUtils.info(e.getMessage());
		}
		return new JSONObject();
	}

	public static void flushCache() {
		Log4jUtils.info("FlushCache");
		String path = String.format("%sres/cms.json", CmsAppUtils.class.getResource("/").getFile());
		try {
			FileUtils.write(new File(path), cmsApp().getJsonObject().toJSONString());
		} catch (Exception e) {
			Log4jUtils.info(e.getMessage());
		}
	}

	public static void flushContext() {
		Log4jUtils.info("FlushContext");
		JSONObject json = cmsApp().getModules();
		ArrayList<String> list = new ArrayList<String>();
		for (Object obj : json.values()) {
			JSONObject subJson = (JSONObject) obj;
			if (subJson.getIntValue("state") == 2) {
				list.add(subJson.getString("basePackage"));
			}
		}
		String[] packages = new String[list.size()];
		list.toArray(packages);
		cmsApp().getApplicationContext().setConfigLocations(null);
		cmsApp().getApplicationContext().register(CmsAppBeansScan.class, CmsAppListener.class);
		if (packages != null && packages.length > 0)
			cmsApp().getApplicationContext().scan(packages);
		cmsApp().getApplicationContext().refresh();
	}

	public static <T extends IModule> void rigsterModule(Class<T> cls) {
		String name = cls.getSimpleName();
		name = String.format("%s%s", Character.toLowerCase(name.charAt(0)), name.substring(1));
		if (cmsApp().getModules().containsKey(name))
			return;
		Module anno = cls.getAnnotation(Module.class);
		JSONObject subJson = new JSONObject();
		subJson.put("state", 0);
		subJson.put("basePackage", anno.basePackage().isEmpty() ? cls.getPackage().getName() : anno.basePackage());
		subJson.put("desc", anno.desc());
		cmsApp().getModules().put(name, subJson);
		changeModuleState(name, anno.state());
		flushCache();
	}

	public static boolean changeModuleState(String name, int state) {
		if (state > 0 && cmsApp().getModules().containsKey(name) && CmsAppUtils.getBean(name, IModule.class).install()) {
			cmsApp().getModules().getJSONObject(name).put("state", state);
			return true;
		}
		return false;
	}

	public static <T extends IModule> boolean changeModuleState(Class<T> cls, int state) {
		String name = cls.getSimpleName();
		name = String.format("%s%s", Character.toLowerCase(name.charAt(0)), name.substring(1));
		return changeModuleState(name, state);
	}

	public static Object getBean(String name) {
		try {
			return cmsApp().getApplicationContext().getBean(name);
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	public static <T> T getBean(String name, Class<T> cls) {
		try {
			return cmsApp().getApplicationContext().getBean(name, cls);
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	public static <T> T getBean(Class<T> cls) {
		try {
			return cmsApp().getApplicationContext().getBean(cls);
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
		return null;
	}

	// public static Logger log(Class<?> cls) {
	// return Logger.getLogger(cls);
	// }

}
