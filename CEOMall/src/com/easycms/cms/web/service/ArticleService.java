package com.easycms.cms.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Authority;
import com.easycms.cms.web.dao.IArticleDao;
import com.easycms.cms.web.utils.CmsWebUtils;

@Service
public class ArticleService {
	private IArticleDao articleDao;

	@Autowired
	public void setArticleDao(IArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public JSONArray catsList() {
		JSONArray json = new JSONArray();
		json.addAll(this.articleDao.catsList());
		return json;
	}

	@Authority(group = "cms", auth = "login")
	public void addCat(JSONObject json) {
		System.out.println("this Fun");
		if (json == null)
			json = CmsWebUtils.request2Json();
		json.put("user_id", CmsWebUtils.getNowUser().getIntValue("user_id"));
		articleDao.addCat(json);
	}

	public void delCat(int id) {
		articleDao.delCat(id);
	}

	public void addArticle(JSONObject json) {
		if (null == json)
			json = CmsWebUtils.request2Json();
		articleDao.addArticle(json);
	}

	public JSONArray articleList() {
		JSONArray json = new JSONArray();
		json.addAll(articleDao.articleList());
		return json;
	}

	public JSONObject queryArticle(int id) {
		System.out.println(articleDao.queryArticle(id));
		return articleDao.queryArticle(id);
	}

	public void delArticle(int id) {
		articleDao.delArticle(id);
	}
}
