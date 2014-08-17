package com.easycms.cms.web.interceptor.impl;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.util.FileUtil;

import com.easycms.cms.annotation.Interceptor;
import com.easycms.cms.impl.AbstractInterceptor;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.interceptor.CmsAppChainInterceptor;
import com.easycms.cms.web.utils.CmsWebUtils;

@Interceptor
public class ResInterceptor extends AbstractInterceptor {

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response, CmsAppChainInterceptor chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		if(uri.endsWith(CmsWebUtils.getSuffixDynamic())){
			chain.doInterceptor();
		}else if(uri.endsWith(CmsWebUtils.getSuffixTemplate())){
			response.setStatus(404);
		}
	
		 else{
			String filePath = CmsWebUtils.getServletContext().getRealPath(String.format("%s%s", CmsWebUtils.getTheme(),uri));
			Matcher matcher = Pattern.compile("\\.\\w+").matcher(uri);
			if(matcher.find()){
				String mimeType = CmsWebUtils.getServletContext().getMimeType(matcher.group(0));
				Log4jUtils.info(String.format("MineType:%s", mimeType));
				response.setContentType(mimeType);
			}else{
				filePath = buildRequest(request, response, uri, filePath);
			}
			if(filePath == null){
				response.setStatus(404);
			}else{
				doResponse(request, response,filePath);
			}
		}
	}



	protected String buildRequest(HttpServletRequest request,
			HttpServletResponse response, String uri, String filePath)
			throws ServletException, IOException {
		String files[] = (String[]) CmsWebUtils.getServletContext().getAttribute("org.apache.catalina.WELCOME_FILES");
		for (int i = 0; files != null &&  i < files.length; i++) {
			if(files[i].endsWith(CmsWebUtils.getSuffixDynamic())){
				request.getRequestDispatcher(String.format("%s%s", uri,files[i])).forward(request, response);
				break;
			}else if(files[i].endsWith(CmsWebUtils.getSuffixTemplate())){
				response.setStatus(404);
				break;
			}else{
				String nowPath = String.format("%s\\%s\\%s", filePath,files[i]);
				File file = new File(nowPath);
				if(file.exists() && file.isFile() && file.canRead()){
					return nowPath;
				}
			}
		}
		return null;
	}
	
	
	
	protected void doResponse(HttpServletRequest request,HttpServletResponse response,String filePath) {
		try {
			byte buffers[] = FileUtil.readAsByteArray(new File(filePath));
			response.setContentLength(buffers.length);
			response.getOutputStream().write(buffers);
		} catch (Exception e) {
			response.setStatus(404);
			Log4jUtils.error(e.getMessage());
		}
	}
	

}
