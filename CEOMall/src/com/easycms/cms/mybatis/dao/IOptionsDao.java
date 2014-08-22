package com.easycms.cms.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IOptionsDao {

	@Select("Select op_key,op_value from cms_options")
	List<Map<String, String>> list();
}
