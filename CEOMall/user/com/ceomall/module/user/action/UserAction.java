package com.ceomall.module.user.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ceomall.module.user.service.UserService;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

@Controller
@RequestMapping({ "/admin", "" })
public class UserAction {
	public String S_USER;
	String user = "";
	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/login.shtml")
	public String login(ModelMap root) {
		JSONObject json = CmsWebUtils.request2Json();
		JSONObject userJson = userService.login(json);
		if (userJson != null) {
			CmsWebUtils.setNowUser(userJson);
			System.out.println(CmsWebUtils.getNowUser());
		}
		return "index";
	}

	@RequestMapping("/logout.shtml")
	public String logout(ModelMap root) {
		CmsWebUtils.setNowUser(null);
		return "index";
	}

	// @Authority(group="user",auth="cms")
	@RequestMapping("/showuser.shtml")
	public String showUser(ModelMap root) {
		Log4jUtils.info("Edit user");
		CmsWebUtils.request2Json();
		List<JSONObject> user = userService.query();
		root.put("users", user);
		return "user";
	}

	// 跳转修改
	@RequestMapping("/tomodify.shtml")
	public String toModify(ModelMap root) {
		JSONObject json = CmsWebUtils.request2Json();
		if (json == null)
			return "index";
		JSONObject jsonobject = userService.toModify(json);
		root.put("users", jsonobject);
		return "detail";
	}

	// 修改
	@RequestMapping("/modify.shtml")
	public String modify(ModelMap root) {
		Log4jUtils.info("Edit user");
		String message = "";
		JSONObject json = CmsWebUtils.request2Json();
		userService.modify(json);
		root.put("msg", message);
		return "index";
	}

	// 插入
	@RequestMapping("/insertuser.shtml")
	public String insert(ModelMap root) {
		Log4jUtils.info("Edit user");
		JSONObject json = CmsWebUtils.request2Json();
		userService.insert(json);
		showUser(root);
		return "user";
	}

	// 删除
	@RequestMapping("/todelete.shtml")
	public String delete(ModelMap root) {
		Log4jUtils.info("Edit user");
		JSONObject json = CmsWebUtils.request2Json();
		userService.delete(json);
		showUser(root);
		return "user";
	}

	// 用户订单详情
	@RequestMapping("/userdesc.shtml")
	public String userdesc(ModelMap root) {
		Log4jUtils.info("Edit user");
		JSONObject json = CmsWebUtils.request2Json();
		List<JSONObject> data = userService.userdesc(json);
		root.put("users", data);
		return "userdesc";
	}

	// 删除订单
	@RequestMapping("/deleteorder.shtml")
	public String deleteorder(ModelMap root) {
		Log4jUtils.info("Edit user");
		JSONObject json = CmsWebUtils.request2Json();
		userService.deleteorder(json);
		userdesc(root);
		return "userdesc";
	}
}
