package com.dudu.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dudu.domain.LearnResource;
import com.dudu.mongo.domin.认购;
import com.dudu.mongo.service.OrderService;

/** 教程页面
 * Created by tengj on 2017/3/13.
 */
@Controller
public class OrderController  extends AbstractController{
	@Autowired
	private OrderService orderService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 查询列表
     * @param page
     * @return
     */
   @RequestMapping(value = "/queryOrderByDays")
    @ResponseBody
    public List<认购> queryOrderByDays(int days){
	   List<认购> list=null;
	   Map<String,Object> map=new HashMap<>();
	   try {
    	list=orderService.queryOrder(days);
	   }catch (Exception e) {
         e.printStackTrace();
   	}
    	if(list==null) {
    		return null;
    	}
    	map.put("total", list.size());
    	map.put("rows",list);
		return list;
    }
    
}