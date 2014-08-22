package com.easycms.cms.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easycms.cms.web.service.AuthorityService;

@Controller
@RequestMapping("/admin")
public class AuthorityAction {
	private AuthorityService authorityService;
	
	@Autowired
	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}


	@RequestMapping("/alist.shtml")
	public String list(@RequestParam(value="t",defaultValue="0")int type,ModelMap root) {
		authorityService.list(0);
		return "list";
	}
}
