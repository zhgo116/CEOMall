package com.easycms.cms.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IAuthorityDao {

	@Select("SELECT COUNT(A.id) FROM cms_role_auth A" 
			+ " JOIN cms_auth B on A.auth_id = B.auth_id " 
			+ "JOIN cms_auth_group C ON B.group_id = C.group_id " 
			+ "WHERE A.role_id=${j.role_id} AND B.auth_name='${j.auth_name}' AND C.group_name='${j.group_name}'")
	int checkAuthority(@Param("j") JSONObject json);

	@Select("SELECT group_id,group_name ,group_desc,auth_count FROM cms_auth_group")
	List<JSONObject> authGroupList();

	@Select("SELECT a.auth_id,a.auth_name,a.auth_desc,b.group_id,b.group_name FROM cms_auth a join cms_auth_group b on a.group_id = b.group_id")
	List<JSONObject> authList();

	@Select("SELECT role_id,role_name,role_desc,role_time FROM cms_role")
	List<JSONObject> roleList();

	@Select("SELECT auth_id FROM cms_role_auth where role_id = ${role_id}")
	List<Integer> authRoleList(@Param("role_id") int role_id);

	@Insert("INSERT INTO cms_role(role_name,role_desc,role_time) VALUES('${j.role_name}','${j.role_desc}',now())")
	public void addRole(@Param("j") JSONObject json);

	@Delete("DELETE FROM cms_role where role_id=${id}")
	public void delRole(@Param("id") int id);

	@Insert("INSERT INTO cms_role_auth(role_id,auth_id) VALUES(${j.role_id},${j.auth_id})")
	public void allowAuth(@Param("j") JSONObject json);

	@Delete("DELETE FROM cms_role_auth WHERE role_id=${j.role_id} and auth_id=${j.auth_id}")
	public void disallowAuth(@Param("j") JSONObject json);
}
