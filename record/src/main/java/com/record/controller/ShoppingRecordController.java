package com.record.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.record.utils.HttpUtil;
import com.record.dao.ShoppingRecordDao;
import com.record.entity.Product;
import com.record.entity.ShoppingRecord;

import org.springframework.beans.factory.annotation.Autowired;

//import com.shopping.service.ProductService;
import com.record.service.ShoppingRecordService;
import com.record.entity.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/3.
 */
@Controller
public class ShoppingRecordController {
//    @Resource
//    private ProductService productService;
	@Resource
	private ShoppingRecordService shoppingRecordService;

	@Autowired
	private ShoppingRecordDao shoppingRecordDao;
    private String userUrl;
    private String productUrl;
    private String shoppingcarUrl;
    private String recordUrl;
    
    public ShoppingRecordController() {
		// TODO Auto-generated constructor stub
		this.userUrl = "http://user-vmall:8080/user";
		this.productUrl = "http://product-vmall:8080/product";
		this.shoppingcarUrl = "http://shoppingcar-vmall:8080/shoppingcar";
		this.recordUrl = "http://record-vmall:8080/record";
		System.out.println("url初始化：\n"+userUrl+"\n"+productUrl+"\n"+shoppingcarUrl+"\n"+recordUrl);
	}
//	@RequestMapping(value = "/shopping_record")
//	// public String shopping_record(int userId, String currentUserNickName,
//	// HttpSession httpSession){
//	public String shopping_record(HttpServletRequest request, HttpSession httpSession) {
//
//		// Session传递
//		// userId、userNickName
//		String userId = request.getParameter("userId");
//		String currentUserNickName = request.getParameter("currentUserNickName");
//		if (userId == null || userId.equals("") || currentUserNickName == null || currentUserNickName.equals("")) {
//			return "shopping_record";
//		}
//		System.out.println("-------currentUserId: " + userId);
//		User user = new User();
//		user.setId(Integer.parseInt(userId));
//		user.setNickName(currentUserNickName);
//		httpSession.setAttribute("currentUser", user);
//
//		return "shopping_record";
//	}

	@RequestMapping(value = "/shopping_handle")
	public String shopping_handle() {
		return "shopping_handle";
	}

	@RequestMapping(value = "/addShoppingRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addShoppingRecord(int userId, int productId, int counts) {
		System.out.println("我添加了" + userId + " " + productId);
		String result = null;

		// Product product = productService.getProduct(productId);
		String url1 = this.productUrl+"/getProductById";
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", String.valueOf(productId));
		String result1 = HttpUtil.sendPost(url1, map);
		System.out.println("---------------result1:\n" + result1);
		Map maps = (Map) JSON.parse(result1);
		String productJsonString = (String) maps.get("result");
		Product product = JSONObject.parseObject(productJsonString, Product.class);// JSON字符串转对象

		if (counts <= product.getCounts()) {
			ShoppingRecord shoppingRecord = new ShoppingRecord();
			shoppingRecord.setUserId(userId);
			shoppingRecord.setProductId(productId);
			shoppingRecord.setProductPrice(product.getPrice() * counts);
			shoppingRecord.setCounts(counts);
			shoppingRecord.setOrderStatus(0);
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			shoppingRecord.setTime(sf.format(date));
			product.setCounts(product.getCounts() - counts);
			// productService.updateProduct(product);
			String url2 = this.productUrl+"/updateProductById";
			Map<String, String> map2 = new HashMap<String, String>();
			String productJsonString2 = JSON.toJSONString(product);
			map2.put("productJsonString", productJsonString2);
			String result2 = HttpUtil.sendPost(url2, map2);
			System.out.println("result2:" + result2);

			shoppingRecordService.addShoppingRecord(shoppingRecord);
			result = "success";
		} else {
			result = "unEnough";
		}
		//result = "unEnough";

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		return resultMap;
	}

	@RequestMapping(value = "/changeShoppingRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeShoppingRecord(int userId, int productId, String time, int orderStatus) {
		System.out.println("我接收了" + userId + " " + productId + " " + time + " " + orderStatus);
		ShoppingRecord shoppingRecord = shoppingRecordService.getShoppingRecord(userId, productId, time);
		System.out.println("我获取到了了" + shoppingRecord.getTime());
		shoppingRecord.setOrderStatus(orderStatus);
		shoppingRecordService.updateShoppingRecord(shoppingRecord);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", "success");
		System.out.println("我成功fanhui了");
		return resultMap;
	}

	@RequestMapping(value = "/getShoppingRecords", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShoppingRecords(int userId) {
		List<ShoppingRecord> shoppingRecordList = shoppingRecordService.getShoppingRecords(userId);
		String shoppingRecords = JSONArray.toJSONString(shoppingRecordList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", shoppingRecords);
		return resultMap;
	}

	@RequestMapping(value = "/getShoppingRecordsByOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getShoppingRecordsByOrderStatus(int orderStatusf) {
		List<ShoppingRecord> shoppingRecordList = shoppingRecordService.getShoppingRecordsByOrderStatus(orderStatusf);
		String shoppingRecords = JSONArray.toJSONString(shoppingRecordList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", shoppingRecords);
		return resultMap;
	}

	@RequestMapping(value = "/getAllShoppingRecords", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getAllShoppingRecords() {

		List<ShoppingRecord> shoppingRecordList = shoppingRecordService.getAllShoppingRecords();
		String shoppingRecords = JSONArray.toJSONString(shoppingRecordList);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", shoppingRecords);

		return resultMap;
	}

	@RequestMapping(value = "/getUserProductRecord", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserProductRecord(int userId, int productId) {
//		// Session传递
//		// userId、userNickName
//		System.out.println("-------currentUserId: " + userId);
//		User user = new User();
//		user.setId(userId);
//		user.setNickName(currentUserNickName);
//		httpSession.setAttribute("currentUser", user);

		String result = "false";
		if (shoppingRecordService.getUserProductRecord(userId, productId)) {
			result = "true";
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		return resultMap;
	}

	// 供user应用调用
	@RequestMapping(value = "/deleteShoppingRecordByUser", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteShoppingRecordByUser(int userId) {
		String result = "false";
		if (shoppingRecordDao.deleteShoppingRecordByUser(userId)) {
			result = "true";
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		return resultMap;
	}

	// 供product应用调用
	@RequestMapping(value = "/deleteShoppingRecordByProductId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteShoppingRecordByProductId(int productId) {
		String result = "false";
		if (shoppingRecordDao.deleteShoppingRecordByProductId(productId)) {
			result = "true";
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", result);
		return resultMap;
	}

	// 供shoppingcar-eva应用调用
	@RequestMapping(value = "/getUserProductRecord", method = RequestMethod.GET)
	@ResponseBody
	public String deleteShoppingRecordByProductId(int userId, int productId) {
		String result = "false";
		if (shoppingRecordService.getUserProductRecord(userId, productId)) {
			result = "true";
		}
		return result;
	}

}
