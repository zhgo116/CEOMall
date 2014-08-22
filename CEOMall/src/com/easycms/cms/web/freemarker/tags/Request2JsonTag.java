package com.easycms.cms.web.freemarker.tags;

import java.io.IOException;

import com.easycms.cms.annotation.Tag;
import com.easycms.cms.utils.Assert;
import com.easycms.cms.web.utils.CmsWebUtils;

import freemarker.template.TemplateException;

@Tag("req2json")
public class Request2JsonTag extends BindTag {

	@Override
	public void renden() throws TemplateException, IOException {
		String name = this.getParam("name");
		String scope = this.getParam("scope");
		Object result = CmsWebUtils.request2Json();
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

}
