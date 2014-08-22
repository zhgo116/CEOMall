package com.easycms.cms.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.alibaba.fastjson.JSONObject;
import com.easycms.cms.annotation.Mapper;

@Mapper
public interface IArticleDao {
	// cat
	@Select("SELECT * FROM cms_article_cat")
	List<JSONObject> catsList();

	@Insert("INSERT INTO cms_article_cat(cat_name,cat_parent_id,cat_time,cat_desc,user_id) VALUES('${j.cat_name}',${j.cat_parent_id},now(),'${j.cat_desc}',${j.user_id})")
	void addCat(@Param("j") JSONObject json);

	@Delete("DELETE FROM cms_article_cat WHERE cat_id=${id}")
	void delCat(@Param("id") int id);

	// article

	@Insert("INSERT INTO cms_article(title,cat_id,author,author_email,article_desc,content,article_time) VALUES('${j.title}',${j.cat_id},'${j.author}','${j.author_email}','${j.article_desc}','${j.content}',now())")
	void addArticle(@Param("j") JSONObject json);

	@Select("SELECT * FROM cms_article")
	List<JSONObject> articleList();

	@Select("SELECT * FROM cms_article WHERE article_id=${id}")
	JSONObject queryArticle(@Param("id") int id);

	@Delete("DELETE FROM cms_article WHERE article_id=${id}")
	void delArticle(@Param("id") int id);
}
