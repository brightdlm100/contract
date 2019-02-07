package com.dudu.mongo.controller;

import com.dudu.controller.AbstractController;
import com.dudu.domain.User;
import com.dudu.mongo.domin.合同;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Controller
public class QueryContractController extends  AbstractController{
	@Autowired
	 private MongoTemplate mongoTemplate;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
/*    @RequestMapping("/toLogin")
    public String toLogin(Model model){
        model.addAttribute("ctx", getContextPath()+"/");
        return "login";
    }*/

    @RequestMapping(value = "/queryContract",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> queryContract(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("ctx",request.getContextPath());
        Map<String,Object> map =new HashMap<String,Object>();
        String userName=request.getParameter("userName");
        String query=".*?"+userName+".*?";
        List<合同> list=mongoTemplate.find(new Query(new Criteria().orOperator(Criteria.where("房间").regex(query),Criteria.where("客户名称").regex(query))), 合同.class);;
       
        String [] info=new String[list.size()];
        int i=0;
        for (合同 c: list) {
			String s=c.客户名称+","+c.房间+","+c.联系电话+","+c.建筑面积+","+c.合同签订日期+","+c.业务员;
			info[i]=s;
			i++;
		}
        map.put("result","1");
        map.put("info", info);
        return map;
    }
    @RequestMapping(value = "/queryContractDetail",method = RequestMethod.POST)
    @ResponseBody
    public List<合同> queryDetail(HttpServletRequest request, HttpServletResponse response){
        request.setAttribute("ctx",request.getContextPath());
        Map<String,Object> map =new HashMap<String,Object>();
        String roomOrName=request.getParameter("contractQryInput");
        List<合同> list=null;
        String[] key=roomOrName.split("-");
		 if(key.length==1) {
			  list=mongoTemplate.find(new Query(new Criteria().orOperator(Criteria.where("房间").regex("^.*?" +key[0]+ ".*$"),Criteria.where("客户名称").regex("^.*?" +key[0]+ ".*$"))), 合同.class);
		 }else {
			  list=mongoTemplate.find(new Query((new Criteria().andOperator(Criteria.where("房间").regex("^.*?" +key[0]+ ".*$"),Criteria.where("房间").regex("^.*?" +key[1]+ ".*$"),Criteria.where("房间").regex("^((?!车位).)*$")))), 合同.class);
		 }
        // String query=".*?"+roomOrName+".*?";
       // List<合同> list=mongoTemplate.find(new Query(new Criteria().orOperator(Criteria.where("房间").regex(query),Criteria.where("客户名称").regex(query))), 合同.class);;
        return list;
    }
}
