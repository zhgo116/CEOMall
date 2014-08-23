package com.ceomall.module.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ceomall.module.user.dao.IUserDao;
import com.easycms.cms.annotation.Authority;

@Service
public class UserService {
	private IUserDao userDao;

	@Autowired
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public JSONObject login(JSONObject json) {
		return userDao.login(json);
	}

	@Authority(group="cms",auth="edit")
	public List<JSONObject> query() {
		return userDao.query();
	}
	public JSONObject toModify(JSONObject json) {
		return userDao.toModify(json);
	}
	
	public List<JSONObject> query1() {
		return userDao.query();
	}
	public void modify(JSONObject json) {
		userDao.modify(json);
	}
	
	public void insert(JSONObject json) {
		userDao.insert(json);
	}
	
	
	public void delete(int json) {
		userDao.delete(json);
	}
	
	public List<JSONObject> userdesc(JSONObject json) {
		return userDao.userdesc(json);
	}
	
	public void deleteorder(JSONObject json) {
		userDao.deleteorder(json);
	}
	
}
