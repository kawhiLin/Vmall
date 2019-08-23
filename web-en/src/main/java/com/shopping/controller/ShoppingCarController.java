package com.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shopping.entity.ShoppingCar;
import com.shopping.utils.InitDB;
import com.shopping.utils.HttpUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/3.
 */
@Controller
public class ShoppingCarController {


    @RequestMapping(value = "/shopping_car")
    public String shopping_car(){
        return "shopping_car";
    }

    @RequestMapping(value = "/addShoppingCar",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addShoppingCar(int userId,int productId,int counts){
    	String url =  UserController.shoppingcarUrl+"/addShoppingCar";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", String.valueOf(userId));
		map.put("productId", String.valueOf(productId));
		map.put("counts", String.valueOf(counts));
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
    	
//    	System.out.println("数量为"+counts);
//        ShoppingCar shoppingCar = shoppingCarService.getShoppingCar(userId,productId);
//        if(shoppingCar == null){
//            ShoppingCar shoppingCar1 = new ShoppingCar();
//            shoppingCar1.setUserId(userId);
//            shoppingCar1.setProductId(productId);
//            shoppingCar1.setCounts(counts);
//            shoppingCar1.setProductPrice(productService.getProduct(productId).getPrice()*counts);
//            shoppingCarService.addShoppingCar(shoppingCar1);
//        }
//        else{
//            shoppingCar.setCounts(shoppingCar.getCounts()+counts);
//            shoppingCar.setProductPrice(productService.getProduct(productId).getPrice()*shoppingCar.getCounts());
//            shoppingCarService.updateShoppingCar(shoppingCar);
//        }
//        Map<String, Object> resultMap = new HashMap<String,Object>();
//        resultMap.put("result","success");
//        System.out.println("我返回了");
//        return resultMap;
    }

    @RequestMapping(value = "/getShoppingCars",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getShoppingCars(int userId){
    	String url = UserController.shoppingcarUrl+"/getShoppingCars";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", String.valueOf(userId));
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
		
//        List<ShoppingCar> shoppingCarList = shoppingCarService.getShoppingCars(userId);
//        String shoppingCars = JSONArray.toJSONString(shoppingCarList);
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//        resultMap.put("result",shoppingCars);
//        return resultMap;
    }

    @RequestMapping(value = "/deleteShoppingCar",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteShoppingCar(int userId,int productId){
    	String url = UserController.shoppingcarUrl+"/getShoppingCars";
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", String.valueOf(userId));
		map.put("productId", String.valueOf(productId));
		String res = HttpUtil.sendPost(url, map);
		System.out.println("----res:\n" + res);
		Map resultMap = (Map) JSON.parse(res);
		return resultMap;
		
//        shoppingCarService.deleteShoppingCar(userId,productId);
//        Map<String, Object> resultMap = new HashMap<String,Object>();
//        resultMap.put("result","success");
//        System.out.println("我返回了");
//        return resultMap;
    }
}
