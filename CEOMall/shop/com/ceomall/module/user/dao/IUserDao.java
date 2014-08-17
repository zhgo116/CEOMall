package com.ceomall.module.user.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IUserDao {
	
	@Select("select * from cms_user where user_name='${j.user}' and user_pass='${j.pass}'")
	JSONObject login(@Param("j") JSONObject json);
	
	@Select("select user_name from cms_user")
	JSONObject query();
}
