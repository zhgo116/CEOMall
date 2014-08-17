package com.easycms.cms.web.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IOptionsDao {

	@Select("Select op_value from cms_options where op_key='${key}'")
	String query(@Param("key") String key);
}
