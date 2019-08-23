package com.shoppingcar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shoppingcar.entity.Product;
import com.shoppingcar.utils.HttpUtil;
import com.shoppingcar.dao.ShoppingCarDao;
import com.shoppingcar.entity.ShoppingCar;
//import com.shoppingcar.service.ProductService;
import com.shoppingcar.service.ShoppingCarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/3.
 */
@Controller
public class ShoppingCarController {
   // @Resource
   // private ProductService productService;
    @Autowired
    private ShoppingCarDao shoppingCarDao;
    
    @Resource
    private ShoppingCarService shoppingCarService;
    private String userUrl;
    private String productUrl;
    private String shoppingcarUrl;
    private String recordUrl;

    public ShoppingCarController() {
		// TODO Auto-generated constructor stub
		this.userUrl = "http://user-vmall:8080/user";
		this.productUrl = "http://product-vmall:8080/product";
		this.shoppingcarUrl = "http://shoppingcar-vmall:8080/shoppingcar";
		this.recordUrl = "http://record-vmall:8080/record";
		System.out.println("url初始化：\n"+userUrl+"\n"+productUrl+"\n"+shoppingcarUrl+"\n"+recordUrl);
	}
    
    @RequestMapping(value = "/shopping_car")
    public String shopping_car(){
        return "shopping_car";
    }

    
    @RequestMapping(value = "/addShoppingCar",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addShoppingCar(int userId,int productId,int counts){
        System.out.println("数量为"+counts);
        ShoppingCar shoppingCar = shoppingCarService.getShoppingCar(userId,productId);
        
        //请求product应用，get product
        String url1 = this.productUrl+"/getProductById";
        Map<String,String> map = new HashMap<String,String>();
    	map.put("id", String.valueOf(productId));
		String result1 = HttpUtil.sendPost(url1, map);
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
        System.out.println("我返回了");
        return resultMap;
    }

    @RequestMapping(value = "/getShoppingCars",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getShoppingCars(int userId){
        List<ShoppingCar> shoppingCarList = shoppingCarService.getShoppingCars(userId);
        String shoppingCars = JSONArray.toJSONString(shoppingCarList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",shoppingCars);
        return resultMap;
    }

    @RequestMapping(value = "/deleteShoppingCar",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteShoppingCar(int userId,int productId){
        shoppingCarService.deleteShoppingCar(userId,productId);
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result","success");
        System.out.println("我返回了");
        return resultMap;
    }
    
    
    //供user应用调用
    @RequestMapping(value = "/deleteShoppingCarByUser",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deleteShoppingCarByUser(int userId){
        String result = "false";
        if(shoppingCarDao.deleteShoppingCarByUser(userId)){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return resultMap;
    }
    
    
    //供product应用调用
    @RequestMapping(value = "/deleteShoppingCarByProduct",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deleteShoppingCarByProduct(int productId){
        String result = "false";
        if(shoppingCarDao.deleteShoppingCarByProduct(productId)){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return resultMap;
    }
    
}
