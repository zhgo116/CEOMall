package com.ceomall.module.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IUserDao {
	
	@Select("select * from cms_user where user_name='${j.user_name}' and user_pass='${j.user_pass}'")
	JSONObject login(@Param("j") JSONObject json);
	
	@Select("select * from cms_user")
	List<JSONObject> query();
	
	@Update("update cms_user set user_name='${j.user_name}',user_email='${j.user_email}',user_pass='${j.user_pass}',theme_name='${j.theme_name}',role_id=${j.role_id},is_admin=${j.is_admin} where user_id='${j.user_id}'")
	void modify(@Param("j")JSONObject json);
	
	/**
	 * 修改之前查询用户id，根据id修改
	 * @param json
	 * @return
	 */
	@Select("select * from cms_user where user_id='${j.user_id}'")
	JSONObject toModify(@Param("j")JSONObject json);
	
	/***
	 * 增加用户
	 * 
	 * @param json
	 * @return
	 */
	@Insert("insert into cms_user(user_name,user_pass,user_email,is_admin,role_id,theme_name) values('${j.user_name}','${j.user_pass}','${j.user_email}',${j.is_admin},${j.role_id},'${j.theme_name}')")
	void insert(@Param("j")JSONObject json);
	
	/***
	 * 删除用户
	 * 
	 * @param json
	 * @return
	 */
	@Delete("delete from cms_user where user_id=${user_id}")
	void delete(@Param("user_id")int user_id);
	
	/***
	 *用户详情
	 * 
	 * @param json
	 * @return
	 */
	@Select("select * from cms_user u join mall_order  m  on u.user_id=m.user_id  join mall_pay_type p on m.order_pay_type_id=m.order_pay_type_id=p.pay_id join mall_user_detail d  on u.user_id  where u.user_id=${j}")
	List<JSONObject> userdesc(@Param("j")int json);
	/***
	 * 删除用户
	 * 
	 * @param json
	 * @return
	 */
	@Delete("delete from mall_order where order_id='${j.order_id}'")
	void deleteorder(@Param("j")JSONObject json);
	
}
