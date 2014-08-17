package com.easycms.cms.web.utils;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.utils.CmsAppUtils;

public class CmsWebUtils extends CmsAppUtils{
	public static final String SC_C_SUF_DY = "suffix_dy";
	public static final String SC_C_SUF_TP = "suffix_dy";
	private static final String SC_D_SUF_DY = ".shtml";
	private static final String SC_D_SUF_TP = ".jtp";
	
	private static ThreadLocal<HttpServletRequest> threadRequest = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> threadResponse = new ThreadLocal<HttpServletResponse>();
	

	public static void setRequest(HttpServletRequest request) {
		threadRequest.set(request);
	}
	
	public static void setResponse(HttpServletResponse response) {
		threadResponse.set(response);
	}
	
	public static HttpServletRequest getRequest() {
		return threadRequest.get();
	}
	
	public static HttpServletResponse getResponse() {
		return threadResponse.get();
	}
	
	public static HttpSession getSession() {
		return getRequest().getSession();
	}
	
	public static ServletContext getServletContext() {
		return CmsAppUtils.cmsApp().getApplicationContext().getServletContext();
	}
	
	
	public static JSONObject request2Json(String...args) {
		HttpServletRequest request = CmsWebUtils.getRequest();
		Assert.notNull(request);
		JSONObject json = new JSONObject();
		if(args == null || args.length == 0){
			Enumeration<?> enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String[] values = request.getParameterValues(key);
				json.put(key, values.length > 1?values:values[0]);
			}
		}else {
			for (String key : args) {
				String[] values = request.getParameterValues(key);
				json.put(key, values == null ? "": values.length > 1?values:values[0]);
			}
		}
		return json;
	}
	
	public static String getTheme() {
		String theme = (String) getSession().getAttribute("THEME");
		return  String.format("/Themes/%s", theme == null ? (String) CmsWebUtils.getServletContext().getAttribute("THEME") : theme);
	}
	
	public static JSONObject getConfiguration() {
		return cmsApp().getConfiguration();
	}
	
	public static String getSuffixDynamic() {
		String suffix = getConfiguration().getString(CmsWebUtils.SC_C_SUF_DY);
		return suffix == null || suffix.isEmpty() ? CmsWebUtils.SC_D_SUF_DY : suffix;
	}
	
	public static String getSuffixTemplate() {
		String suffix = getConfiguration().getString(CmsWebUtils.SC_C_SUF_TP);
		return suffix == null || suffix.isEmpty() ? CmsWebUtils.SC_D_SUF_TP : suffix;
	}
}
