package com.dudu.mongo.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.dudu.Application;
import com.dudu.mongo.domin.User;
import com.dudu.mongo.service.UserService;

public class contractMain {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext applicationContext= SpringApplication.run(Application.class,args);
		
		List<User> list = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
        	Thread.sleep(1000);
            list.add(new User(new Date().getTime()+"",i, "" + i, i));
        }
        UserService userService=(UserService) applicationContext.getBean("userService");
        userService.insertAll(list);	
	}
}
