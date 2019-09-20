package com.shoppingcar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shoppingcar.entity.Product;

import com.shoppingcar.entity.ShoppingCar;

import com.shoppingcar.service.ShoppingCarService;


import com.shoppingcar.utils.HttpUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class ShoppingCarController {

    @Resource
    private ShoppingCarService shoppingCarService;

    public static String userUrl;
    public static String productUrl;
    public static String shoppingcarUrl;
    public static String recordUrl;
    public static String evaluationUrl;

//    RestTemplate restTemplate = new RestTemplate();

    public ShoppingCarController() {
//        this.userUrl = "http://127.0.0.1:8081";
//        this.productUrl = "http://127.0.0.1:8082";
//        this.shoppingcarUrl = "http://127.0.0.1:8083";
//        this.recordUrl = "http://127.0.0.1:8084";
//        this.evaluationUrl = "http://127.0.0.1:8085";

        this.userUrl = "http://user:8081";
        this.productUrl = "http://product:8082";
        this.shoppingcarUrl = "http://shoppingcart:8083";
        this.recordUrl = "http://order:8084";
        this.evaluationUrl = "http://evaluation:8085";
        System.out.println("url初始化：\n" + userUrl + "\n" + productUrl + "\n" + shoppingcarUrl + "\n" + recordUrl + "\n"+ evaluationUrl);
    }


    
    @RequestMapping(value = "/addShoppingCar",method = RequestMethod.POST)
    public Map<String, Object> addShoppingCar(int userId, int productId, int counts) {
//            //(int userId,int productId,int counts){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String userId = (String)map.get("userId");
//        String productId = (String)map.get("productId");
//        String counts = (String)map.get("counts");

        System.out.println("数量为"+counts);
        ShoppingCar shoppingCar = shoppingCarService.getShoppingCar(userId,productId);
        
        //请求product应用，get product
        String url1 = this.productUrl+"/getProductById";
//		String result1 = HttpUtil.sendPost(url1, reqMap);
//        String result1 = restTemplate.postForObject(url1,argsBean,String.class);
        Map map = new HashMap();
        map.put("userId",String.valueOf(userId));
        map.put("id",String.valueOf(productId));
        map.put("counts",String.valueOf(counts));
        String result1 = HttpUtil.sendPost(url1,map);


		System.out.println("---------------result1:\n"+result1);
		Map maps = (Map)JSON.parse(result1);
	    String productJsonString = (String)maps.get("result");
		Product product=JSONObject.parseObject(productJsonString, Product.class);//JSON字符串转对象
		
        if(shoppingCar == null){
            ShoppingCar shoppingCar1 = new ShoppingCar();
            shoppingCar1.setUserId(userId);
            shoppingCar1.setProductId(productId);
            shoppingCar1.setCounts(counts);
            
           // shoppingCar1.setProductPrice(productService.getProduct(productId).getPrice()*counts);
    		shoppingCar1.setProductPrice(product.getPrice()*counts);
    		
            shoppingCarService.addShoppingCar(shoppingCar1);
        }
        else{
            shoppingCar.setCounts(shoppingCar.getCounts()+counts);
            //shoppingCar.setProductPrice(productService.getProduct(productId).getPrice()*shoppingCar.getCounts());
            shoppingCar.setProductPrice(product.getPrice()*shoppingCar.getCounts());
            
            shoppingCarService.updateShoppingCar(shoppingCar);
        }
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result","success");
        // for页面显示不同版本的购物车，增加version字段
        resultMap.put("version","1.0");
        // resultMap.put("version","2.0");
        System.out.println("我返回了");
        return resultMap;
    }

    @RequestMapping(value = "/getShoppingCars",method = RequestMethod.POST)
    public Map<String, Object> getShoppingCars(int userId){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String userId = (String)map.get("userId");

        List<ShoppingCar> shoppingCarList = shoppingCarService.getShoppingCars(userId);
        String shoppingCars = JSONArray.toJSONString(shoppingCarList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",shoppingCars);
        return resultMap;
    }

    @RequestMapping(value = "/deleteShoppingCar",method = RequestMethod.POST)
    public Map<String, Object> deleteShoppingCar(int userId, int productId){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String userId = (String)map.get("userId");
//        String productId = (String)map.get("productId");

        shoppingCarService.deleteShoppingCar(userId,productId);
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result","success");
        return resultMap;
    }
    
    
    //供user应用调用
    @RequestMapping(value = "/deleteShoppingCarByUser",method = RequestMethod.POST)
    public Map<String, Object> deleteShoppingCarByUser(int userId){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String userId = (String)map.get("userId");

        String result = "false";
        if(shoppingCarService.deleteShoppingCarByUser(userId)){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return resultMap;
    }
    
    
    //供product应用调用
    @RequestMapping(value = "/deleteShoppingCarByProduct",method = RequestMethod.POST)
    public Map<String, Object> deleteShoppingCarByProduct(int productId){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String productId = (String)map.get("productId");

        String result = "false";
        if(shoppingCarService.deleteShoppingCarByProduct(productId)){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return resultMap;
    }
    
}
