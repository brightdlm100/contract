package com.dudu.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dudu.model.PaySaPi;
import com.dudu.util.PayUtil;


@Controller
@RequestMapping("/pays")
public class PayController {
	@RequestMapping("")
	public String payindex() {
		return "payindex";
	}

	@RequestMapping("/pay")
	@ResponseBody
	public Map<String, Object> pay(HttpServletRequest request, String price, int istype) throws UnsupportedEncodingException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> remoteMap = new HashMap<String, Object>();
		remoteMap.put("price", price);
		remoteMap.put("istype", istype);
		remoteMap.put("orderid", PayUtil.getOrderIdByUUId());
		remoteMap.put("orderuid", "12345678");
		remoteMap.put("goodsname", "bitcoin");
		
		resultMap.put("data", PayUtil.payOrder(remoteMap));
		resultMap.put("code", "1");
		return resultMap;
	}

	@RequestMapping("/notifyPay")
	public void notifyPay(HttpServletRequest request, HttpServletResponse response, PaySaPi paySaPi) throws UnsupportedEncodingException {
		// 保证密钥一致性
		if (PayUtil.checkPayKey(paySaPi)) {
			// TODO 做自己想做的
			System.out.println("支付成功！");
		} else {
			// TODO 该怎么做就怎么做
			System.out.println("支付失败！");
		}
	}

	@RequestMapping("/returnPay")
	public ModelAndView returnPay(HttpServletRequest request, HttpServletResponse response, String orderid) {
		boolean isTrue = false;
		ModelAndView view = null;
		// 根据订单号查找相应的记录:根据结果跳转到不同的页面
		if (isTrue) {
			view = new ModelAndView("index");
		} else {
			view = new ModelAndView("login");
		}
		return view;
	}
}
