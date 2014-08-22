package com.easycms.cms.mybatis.notify;

import java.util.List;
import java.util.Map;

import com.easycms.cms.annotation.Notify;
import com.easycms.cms.mybatis.dao.IOptionsDao;
import com.easycms.cms.utils.Log4jUtils;
import com.easycms.cms.web.utils.CmsWebUtils;

public class InitOptionNotify {

	@Notify("initCMS")
	public void initOptions() {
		Log4jUtils.info("Load configuration from the database!");
		List<Map<String, String>> list = CmsWebUtils.getBean(IOptionsDao.class).list();
		for (Map<String, String> map : list) {
			CmsWebUtils.cmsApp().getConfiguration().put(map.get("op_key"), map.get("op_value"));
		}
	}
}
