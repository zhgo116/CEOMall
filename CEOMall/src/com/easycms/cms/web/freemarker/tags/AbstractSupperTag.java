package com.easycms.cms.web.freemarker.tags;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.annotation.Scope;

import com.easycms.cms.utils.Assert;
import com.easycms.cms.utils.Log4jUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.WrappingTemplateModel;

@Scope("singleton")
public abstract class AbstractSupperTag implements TemplateDirectiveModel {

	protected Environment env;
	protected Map<String, Object> params;
	protected TemplateModel[] vars;
	protected TemplateDirectiveBody body;

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final void execute(Environment arg0, Map arg1, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		this.env = arg0;
		this.params = arg1;
		this.vars = arg2;
		this.body = arg3;
		try {
			this.renden();
		} catch (Exception e) {
			Log4jUtils.error(e.getMessage());
		}
	}

	public abstract void renden() throws TemplateException, IOException;

	public String getParam(String name) {
		if (this.params.containsKey(name)) {
			Object obj = this.params.get(name);
			if (null != obj)
				return ((SimpleScalar) obj).getAsString();
		}
		return null;
	}

	public boolean bindVar(int idx, Object value) {
		if (this.vars.length > idx && null != value) {
			try {
				this.vars[idx] = WrappingTemplateModel.getDefaultObjectWrapper().wrap(value);
				return true;
			} catch (TemplateModelException e) {
				Log4jUtils.error(e.getMessage());
			}
		}
		return false;
	}

	public boolean bindNamespaceVal(String name, Object value) {
		if (Assert.isNull(name, value) || Assert.isEmpty(name))
			return false;
		this.env.getCurrentNamespace().put(name, value);
		return true;
	}

	public boolean bindGlobalVal(String name, Object value) {
		if (Assert.isNull(name, value) || Assert.isEmpty(name))
			return false;
		try {
			this.env.getConfiguration().setSharedVariable(name, value);
			return true;
		} catch (TemplateModelException e) {
			Log4jUtils.error(e.getMessage());
		}
		return false;
	}
}
