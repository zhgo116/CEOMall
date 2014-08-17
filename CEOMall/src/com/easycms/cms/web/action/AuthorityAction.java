package com.easycms.cms.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easycms.cms.web.dao.IAuthorityDao;

@Controller
@RequestMapping("/admin")
public class AuthorityAction {
	private IAuthorityDao authorityDao;
	
	@Autowired
	public void setAuthorityDao(IAuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	@RequestMapping("/alist.shtml")
	public String list(@RequestParam(value="t",defaultValue="0")int type,ModelMap root) {
		root.put("t", type);
		switch (type) {
		case 0:
			root.put("msg", "param is emty!");
			return "/msg";
		case 1:
			root.put("list", authorityDao.roleList());
			return "auth_list";
		case 2:
			root.put("list", authorityDao.authList());
			System.out.println(root.get("list"));
			return "auth_list";
		default:
			break;
		}
		return null;
	}
}
