package com.example.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Note: 获取上下文的bean
 */

@Component
public class MyApplicationContextUtil implements ApplicationContextAware {
	
	// 声明一个静态变量保存   
	private static ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		MyApplicationContextUtil.applicationContext=applicationContext;   
	}
	
	public static ApplicationContext getContext(){
		return applicationContext;
	}  
	
	@SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
               return (T) applicationContext.getBean(name);
     }

}