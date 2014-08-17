package com.ceomall.module.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ceomall.module.user.dao.IUserDao;

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
	
	public JSONObject query() {
		return userDao.query();
	}
}
