package com.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.entity.User;
import com.shopping.entity.UserDetail;
import com.shopping.utils.InitDB;
import com.shopping.utils.HttpUtil;
import com.shopping.utils.Response;

import org.apache.commons.io.FileUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by 14437 on 2017/3/1.
 */
@Controller
public class UserController {

	@Resource
	private SessionFactory sessionFactory;


	public static String userUrl;
	public static String productUrl;
	public static String shoppingcarUrl;
	public static String recordUrl;

	UserController() {
		//当应用运行时，初始化数据库（单实例）
		System.out.println("开始初始化数据库....");
		InitDB.connmysql();
		System.out.println("初始化数据库完成");
		this.userUrl = "http://user-vmall:8080/user";
		this.productUrl = "http://product-vmall:8080/product";
		this.shoppingcarUrl = "http://shoppingcar-vmall:8080/shoppingcar";
		this.recordUrl = "http://record-vmall:8080/record";
	}

	@RequestMapping(value = "/resetDb")
	@ResponseBody
	public Map<String, Object> resetDb() {
		System.out.println("****resetDb****");

//		//初始化数据库
		String resultString = InitDB.connmysql();
		System.out.println("****resetDb2****");
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("result",resultString);
		return res;
	}

	@RequestMapping(value = "/register")
	public String register() {
		return "register";
	}

	@RequestMapping(value = "/amend_info")
	public String amend_info() {
		return "amend_info";
	}

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/main")
	public String main() {
		return "main";
	}

	@RequestMapping(value = "/control")
	public String control() {
		return "control";
	}

	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doLogin(String userNameOrEmail, String password, HttpSession httpSession) {

		String url = this.userUrl + "/doLogin";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userNameOrEmail", userNameOrEmail);
		map.put("password", password);
		System.out.println("********\n SendPost："+url);
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		String result = (String) resultMap.get("result");
		if (result.equals("success")) {
			JSONObject userJsonString = (JSONObject) resultMap.get("currentUser");
			User user = JSONObject.toJavaObject(userJsonString, User.class);
			System.out.println("currentUser=" + userJsonString);
			httpSession.setAttribute("currentUser", user);
		}
		System.out.println("我接收到了登录请求" + userNameOrEmail + " " + password);

		return resultMap;
	}

	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doRegister(String userName, String email, String nickName, String password,
			String phoneNumber, int sex, String birthday, String postNumber, String address) {

		String url = this.userUrl + "/doRegister";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("email", email);
		map.put("nickName", nickName);
		map.put("password", password);
		map.put("phoneNumber", phoneNumber);
		map.put("sex", String.valueOf(sex));
		map.put("birthday", birthday);
		map.put("postNumber", postNumber);
		map.put("address", address);

		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
	}

	@RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doUpdate(String userName, String email, String nickName, String password,
			String phoneNumber, int sex, String birthday, String postNumber, String address) {
		String url = this.userUrl + "/doUpdate";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("email", email);
		map.put("nickName", nickName);
		map.put("password", password);
		map.put("phoneNumber", phoneNumber);
		map.put("sex", String.valueOf(sex));
		map.put("birthday", birthday);
		map.put("postNumber", postNumber);
		map.put("address", address);

		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
	}

	@RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllUser() {
		String url = this.userUrl + "/getAllUser";
		Map<String, String> map = new HashMap<String, String>();
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
	}

	// 2018.04.08 修改BUG 这种方法为前后端交互推荐写法
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Response deleteUser(int id) {
		String url = this.userUrl + "/deleteUser";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		String res = HttpUtil.sendPost(url, map);
		JSONObject userJsonString = JSONObject.parseObject(res);
		Response response = JSONObject.toJavaObject(userJsonString, Response.class);
		return response;
	}

	@RequestMapping(value = "/getUserAddressAndPhoneNumber", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserAddressAndPhoneNumber(int id) {
		String url = this.userUrl + "/getUserAddressAndPhoneNumber";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
	}

	@RequestMapping(value = "/doLogout")
	public String doLogout(HttpSession httpSession) {
		httpSession.setAttribute("currentUser", "");
		return "redirect:login";
	}

	@RequestMapping(value = "/getUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserById(int id) {
		String url = this.userUrl + "/getUserById";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
	}

	@RequestMapping(value = "/getUserDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserDetailById(int id) {
		String url = this.userUrl + "/getUserDetailById";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(id));
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;

	}
}
