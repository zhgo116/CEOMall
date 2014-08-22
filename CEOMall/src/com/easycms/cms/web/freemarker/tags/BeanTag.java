package com.easycms.cms.web.freemarker.tags;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.easycms.cms.annotation.Tag;
import com.easycms.cms.utils.Assert;
import com.easycms.cms.utils.ReflationUtils;
import com.easycms.cms.utils.RegexUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateException;
import freemarker.template.WrappingTemplateModel;

@Tag("bean")
public class BeanTag extends AbstractSupperTag {

	@Override
	public void renden() throws TemplateException, IOException {
		if (Assert.isContainKey(this.params, "ref", "fun")) {
			String ref = ((SimpleScalar) this.params.get("ref")).getAsString();
			String fun = ((SimpleScalar) this.params.get("fun")).getAsString();
			Object bean = CmsWebUtils.getBean(ref);
			if (Assert.isNull(bean) || Assert.isEmpty(ref) || Assert.isEmpty(fun))
				return;
			String params = Assert.isContainKey(this.params, "params") ? ((SimpleScalar) this.params.get("params")).getAsString() : null;
			Method method = null;
			List<Object> args = new LinkedList<Object>();
			;
			if (null == params) {
				// û�в���
				method = ReflationUtils.getMethod(bean.getClass(), fun);
			} else if (Assert.notEmpty(params)) {
				// �в���
				method = checkMethod(bean.getClass(), fun, params, args);
			}
			if (null == method)
				return;
			Object[] tempArgs = new Object[args.size()];
			args.toArray(tempArgs);
			Object result = ReflationUtils.invoke(bean, method, tempArgs);
			if (null != result)
				this.vars[0] = WrappingTemplateModel.getDefaultObjectWrapper().wrap(result);
			this.body.render(this.env.getOut());
		}
	}

	protected Method checkMethod(Class<?> clazz, String fun, String params, List<Object> args) {
		String[][] matcheds = RegexUtils.match("&?[\\w\\.]+", params);
		Method[] methods = ReflationUtils.getMethods(clazz, fun);
		if (null == methods || methods.length == 0)
			return null;
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getParameterTypes().length == matcheds.length && checkArgs(matcheds, method, args))
				return method;
		}
		return null;
	}

	protected boolean checkArgs(String[][] matcheds, Method method, List<Object> args) {
		Class<?>[] classes = method.getParameterTypes();
		for (int i = 0; i < classes.length; i++) {
			Object temp = (matcheds[i][0].startsWith("&")) ? getValue(matcheds[i][0].substring(1), classes[i]) : convert(classes[i], matcheds[i][0]);
			// if (matcheds[i][0].startsWith("&")) {
			// String name = matcheds[i][0].substring(1);
			// Object temp = getValue(name, classes[i]);
			// }
			// Object temp = convert(classes[i], matcheds[i][0]);
			if (null == temp)
				return false;
			args.add(temp);
		}
		return true;
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
