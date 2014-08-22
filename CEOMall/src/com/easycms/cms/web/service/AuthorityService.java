package com.easycms.cms.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.dao.IAuthorityDao;
import com.easycms.cms.web.utils.CmsWebUtils;

@Service
public class AuthorityService {

	private IAuthorityDao authorityDao;

	@Autowired
	public void setAuthorityDao(IAuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	public JSONArray list(int i) {

		JSONArray json = new JSONArray();
		switch (i) {
		case 0:
			json.addAll(authorityDao.authGroupList());
			break;
		case 1:
			json.addAll(authorityDao.authList());
			break;
		case 2:
			json.addAll(authorityDao.roleList());
			break;
		default:
			break;
		}
		return json;

	}

	public JSONArray queryAuthRole(int role_id) {
		if (role_id < 0)
			return null;
		JSONArray json = new JSONArray();
		json.addAll(authorityDao.authRoleList(role_id));
		return json;
	}

	public void addRole() {
		JSONObject json = CmsWebUtils.request2Json();
		Log4jUtils.info(json);
		authorityDao.addRole(json);
	}

	public void delRole(int id) {
		authorityDao.delRole(id);
	}

	public void changeAuths() {
		JSONObject json = CmsWebUtils.request2Json();
		if (json.getString("isSel").equals("y"))
			authorityDao.disallowAuth(json);
		else
			authorityDao.allowAuth(json);
	}
}
