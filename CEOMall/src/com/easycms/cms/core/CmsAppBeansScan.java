package com.easycms.cms.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.easycms.cms.annotation.Interceptor;
import com.easycms.cms.annotation.Kernel;
import com.easycms.cms.annotation.Module;
import com.easycms.cms.annotation.Tag;

@Configuration
@ComponentScan(basePackages="/",useDefaultFilters=false,includeFilters=@Filter(type=FilterType.ANNOTATION,value={Module.class,Interceptor.class,Kernel.class,Tag.class}))
public class CmsAppBeansScan {

}
