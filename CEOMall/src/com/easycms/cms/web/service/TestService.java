package com.easycms.cms.web.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	public void t1() {
		System.out.println("t1 run");
	}
	
	public String t2() {
		System.out.println("t2 run");
		return "Tom";
	}
	
	public String t3(int i,float f,TestService tom) {
		System.out.println("t3 run:" + tom);
		return i + ":" + f;
	}
}
