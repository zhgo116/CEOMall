package com.easycms.cms.web.freemarker.tags;

import java.io.IOException;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.easycms.cms.annotation.Tag;
import com.easycms.cms.utils.Assert;
import com.easycms.cms.utils.ReflationUtils;
import com.easycms.cms.utils.RegexUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

import freemarker.template.TemplateException;

@Tag("bind")
public class BindTag extends AbstractSupperTag {

	@Override
	public void renden() throws TemplateException, IOException {
		if (!this.params.containsKey("value"))
			return;
		String name = this.getParam("name");
		String value = fillValue(this.getParam("value"));
		String scope = this.getParam("scope");
		Class<?> clazz = null;
		if (this.params.containsKey("class")) {
			clazz = ReflationUtils.findClass(this.getParam("class"));
		}
		Object result = clazz == null ? JSON.parse(value) : JSON.parseObject(value, clazz);
		if (scope == null || Assert.isEmpty(scope) || scope.equalsIgnoreCase("body"))
			this.bindVar(0, result);
		else if (scope.equalsIgnoreCase("page"))
			this.bindNamespaceVal(name, result);
		else if (scope.equalsIgnoreCase("globar"))
			this.bindGlobalVal(name, result);
		else if (scope.equalsIgnoreCase("request"))
			CmsWebUtils.getRequest().setAttribute(name, result);
		else if (scope.equalsIgnoreCase("session"))
			CmsWebUtils.getSession().setAttribute(name, result);
		else if (scope.equalsIgnoreCase("application"))
			CmsWebUtils.getServletContext().setAttribute(name, result);

		this.body.render(this.env.getOut());
	}

	public String fillValue(String value) {
		if (Assert.isEmpty(value))
			return null;
		StringBuilder builder = new StringBuilder(value);
		String[][] matchs = RegexUtils.match("&(\\w+)", value);
		for (String[] strings : matchs) {
			String result = (String) getValue(strings[1], String.class);
			result = result == null ? "\"\"" : String.format("\"%s\"", result);
			int idx = builder.indexOf(strings[0]);
			builder.replace(idx, strings[0].length() + idx, result);
		}
		return builder.toString();
	}

	protected Object getValue(String name, Class<?> clazz) {
		Object result = CmsWebUtils.getRequest().getParameter(name);
		if (null != result && null != (result = convert(clazz, (String) result)))
			return result;
		result = CmsWebUtils.getRequest().getAttribute(name);
		if (null != result && clazz.isInstance(result))
			return result;
		result = CmsWebUtils.getSession().getAttribute(name);
		if (null != result && clazz.isInstance(result))
			return result;
		result = CmsWebUtils.getServletContext().getAttribute(name);
		if (null != result && clazz.isInstance(result))
			return result;
		result = CmsWebUtils.getBean(name);
		if (null != result && clazz.isInstance(result))
			return result;
		return null;
	}

	protected Object convert(Class<?> clazz, String value) {
		if (clazz == int.class || clazz == Integer.class)
			clazz = Integer.class;
		else if (clazz == long.class || clazz == Long.class)
			clazz = Long.class;
		else if (clazz == float.class || clazz == Float.class)
			clazz = Float.class;
		else if (clazz == double.class || clazz == Double.class)
			clazz = Double.class;
		else if (clazz == byte.class || clazz == Byte.class)
			clazz = Byte.class;
		else if (clazz == boolean.class || clazz == Boolean.class)
			clazz = Boolean.class;
		else if (clazz == char.class || clazz == Character.class)
			clazz = Character.class;
		else if (clazz == short.class || clazz == Short.class)
			clazz = Short.class;
		else if (clazz == String.class)
			return value;
		else
			return null;
		Method method = ReflationUtils.getMethod(clazz, "valueOf", String.class);
		if (null != method)
			return ReflationUtils.invoke(null, method, value);
		return null;
	}
}
