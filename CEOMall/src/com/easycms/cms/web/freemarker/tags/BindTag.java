package com.easycms.cms.web.freemarker.tags;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.easycms.cms.annotation.Tag;
import com.easycms.cms.utils.Assert;
import com.easycms.cms.utils.ReflationUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

import freemarker.template.TemplateException;

@Tag(name = "bind")
public class BindTag extends AbstractSupperTag {

	@Override
	public void renden() throws TemplateException, IOException {
		if (!this.params.containsKey("value"))
			return;
		String name = this.getParam("name");
		String value = this.getParam("value");
		String scope = this.getParam("scope");
		Class<?> clazz = null;
		if (this.params.containsKey("class")) {
			clazz = ReflationUtils.findClass(this.getParam("class"));
		}
		Object result = clazz == null ? JSON.parse(value) : JSON.parseObject(value, clazz);
		if(scope == null || Assert.isEmpty(scope) || scope.equalsIgnoreCase("body"))
			this.bindVar(0, result);
		else if (scope.equalsIgnoreCase("page"))
			this.bindNamespaceVal(name, value);
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

}
