package com.easycms.cms.pointcut;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Authority;
import com.easycms.cms.annotation.Kernel;
import com.easycms.cms.web.dao.IAuthorityDao;
import com.easycms.cms.web.utils.CmsWebUtils;

@Kernel
@Aspect
@Service
@EnableAspectJAutoProxy
public class KernelsPointcut {
	/**
	 * 主题切面
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(@org.springframework.web.bind.annotation.RequestMapping * *(..))")
	public Object requestAround(ProceedingJoinPoint joinPoint) throws Throwable {
		String result = (String) joinPoint.proceed();
		if (!result.startsWith("/")) {
			String url = CmsWebUtils.getRequest().getRequestURI().substring(CmsWebUtils.getRequest().getContextPath().length());
			Matcher matcher = Pattern.compile("(/\\w+)*/").matcher(url);
			if (matcher.find()) {
				result = String.format("%s%s", matcher.group(0), result);
			}
		}
		System.out.println("requestAround:"+String.format("%s%s%s", CmsWebUtils.getTheme(), result, CmsWebUtils.getSuffixTemplate()));
		return String.format("%s%s%s", CmsWebUtils.getTheme(), result, CmsWebUtils.getSuffixTemplate());
	}

	/**
	 * 权限切面
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(@com.easycms.cms.annotation.Authority * *(..))")
	public Object authorityAround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Authority anno = signature.getMethod().getAnnotation(Authority.class);
		JSONObject userJson = CmsWebUtils.getNowUser();
		if (null != userJson && null != anno) {
			JSONObject json = new JSONObject();
			json.put("role_id", userJson.getIntValue("role_id"));
			json.put("auth_name", anno.auth());
			json.put("group_name", anno.group());
			System.out.println("CmsWebUtils.getBean(IAuthorityDao.class):"+CmsWebUtils.getBean(IAuthorityDao.class));
			if (CmsWebUtils.getBean(IAuthorityDao.class).checkAuthority(json) > 0)
				return joinPoint.proceed();
		}
		CmsWebUtils.getResponse().setStatus(401);
		return null;
	}
}
