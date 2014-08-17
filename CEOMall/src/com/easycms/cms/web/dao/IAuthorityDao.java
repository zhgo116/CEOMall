package com.easycms.cms.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IAuthorityDao {

	@Select("SELECT count(a.id) "
			+ "FROM cms_role_auth a "
			+ "join cms_auth b on a.auth_id = b.auth_id "
			+ "join cms_auth_group c on b.group_id=c.group_id "
			+ "where b.auth_name='${authName}' and c.group_name='${groupName}'")
	int checkAuthority(@Param("groupName") String groupName,@Param("authName") String authName);
	
	@Select("SELECT * FROM cms_role")
	List<JSONObject> roleList();
	
	@Select("SELECT auth_id,a.group_id,group_name,auth_name,auth_desc FROM cms_auth a JOIN cms_auth_group b ON a.group_id = b.group_id")
	List<JSONObject> authList();
}
