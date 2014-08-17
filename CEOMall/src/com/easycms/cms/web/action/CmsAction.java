package com.easycms.cms.web.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

@Controller
public class CmsAction {

	@RequestMapping("**")
	public String doRequest(HttpServletRequest request,HttpServletResponse response) {
		String uri = request.getRequestURI().substring(request.getContextPath().length()).replace(CmsWebUtils.getSuffixDynamic(),"");
		String filePath = CmsWebUtils.getServletContext().getRealPath(String.format("%s%s%s",CmsWebUtils.getTheme(),uri,CmsWebUtils.getSuffixTemplate()));
		Log4jUtils.info(String.format("RequestUri:%s",uri));
		if(!(new File(filePath).exists())){
			Log4jUtils.info(String.format("%s is not found", filePath));
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "404";
		}
		return uri;
	}

}
