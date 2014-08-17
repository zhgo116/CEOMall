package com.ceomall.module.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



import com.alibaba.fastjson.JSONObject;
import com.ceomall.module.user.service.UserService;
import com.easycms.cms.annotation.Authority;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

@Controller
@RequestMapping({"/admin",""})
public class UserAction {
	public  static final String S_USER = "USER";
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/login.shtml")
	public String login(ModelMap root) {
		JSONObject json = CmsWebUtils.request2Json();
		JSONObject userJson = userService.login(json);
		if(userJson != null){
			CmsWebUtils.getSession().setAttribute(S_USER, userJson);
		}
		return "/msg";
	}
	
	@RequestMapping("/logout.shtml")
	public String logout(ModelMap root) {
		CmsWebUtils.getSession().removeAttribute(S_USER);
		return "/msg";
	}
	
//	@Authority(group="user",auth="cms")
	@RequestMapping("/edit.shtml")
	public String editInfo(ModelMap root) {
		Log4jUtils.info("Edit user");
		String message="";
		JSONObject json = CmsWebUtils.request2Json();
		JSONObject userJson = userService.query();
		if(userJson != null){
			CmsWebUtils.getSession().setAttribute(S_USER, userJson);
			message+=userJson;
		}
		root.put("msg", message);
		return "user";
	}
	
	@Authority(group="user",auth="list")
	@RequestMapping("/list.shtml")
	public String list() {
		
		return "list";
	}
}
