package com.product.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.product.entity.Product;

import com.product.service.ProductService;
import com.product.utils.ArgsBean;



import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EnableAutoConfiguration
@RestController
public class ProductController {
    @Resource
    private ProductService productService;

    public static String userUrl;
    public static String productUrl;
    public static String shoppingcarUrl;
    public static String recordUrl;
    public static String evaluationUrl;

    public ProductController() {
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
    
    @RequestMapping(value = "/getAllProducts", method = RequestMethod.POST)
    public String getAllProducts(){
        return JSONObject.toJSONString(productService.getAllProduct());
    }

    //没用到？
//    @RequestMapping(value = "/updateProductCounts", method = RequestMethod.POST)
//    public String updateProductCounts(@RequestBody ArgsBean argsBean){
//        return JSONObject.toJSONString(productService.updateProductCounts(argsBean));
//    }


//    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
//    public String deleteProduct(@RequestBody ArgsBean argsBean) {
//        return JSONObject.toJSONString(productService.deleteProduct(argsBean));
//    }
//
//    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
//    public String addProduct(@RequestBody ArgsBean argsBean) {
//        return JSONObject.toJSONString(productService.addProduct(argsBean));
//    }



    @RequestMapping(value = "/productDetail", method = RequestMethod.POST)
    public Map<String, Object> productDetail(int id) {
        Product product = productService.getProductById(id);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result","success");
        resultMap.put("productDetail",product);
        return resultMap;
    }


    @RequestMapping(value = "/searchProduct", method = RequestMethod.POST)
    public String searchProduct(@RequestBody ArgsBean argsBean){
        List<Product> productList = new ArrayList<Product>();
        productList = productService.getProductsByKeyWord(argsBean);
        String searchResult = JSONArray.toJSONString(productList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",searchResult);
        System.out.println("我返回了"+searchResult);
        return JSONObject.toJSONString(resultMap);
    }

    @RequestMapping(value = "/getProductById", method = RequestMethod.POST)
    public Map<String, Object> getProductById(int id) {
        Product product = productService.getProductById(id);
        String result = JSON.toJSONString(product);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        System.out.println("getProductById return:"+JSONObject.toJSONString(resultMap));
        return resultMap;
    }

//    MultipartFile暂不支持
//    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//    public String uploadFile(@RequestParam MultipartFile productImgUpload,String name, HttpServletRequest request) {
//        String result = "fail";
//        try{
//            if(productImgUpload != null && !productImgUpload.isEmpty()) {
//                String fileRealPath = request.getSession().getServletContext().getRealPath("/static/img");
//                int id = productService.getProductByName(name).getId();
//                String fileName = String.valueOf(id)+".jpg";
//                File fileFolder = new File(fileRealPath);
//                System.out.println("fileRealPath=" + fileRealPath+"/"+fileName);
//                if(!fileFolder.exists()){
//                    fileFolder.mkdirs();
//                }
//                File file = new File(fileFolder,fileName);
//                productImgUpload.transferTo(file);
//                result = "success";
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        Map<String,Object> resultMap = new HashMap<String,Object>();
//        resultMap.put("result",result);
//        return JSONObject.toJSONString(resultMap);
//    }
//
//    //for 上传图片
//    @RequestMapping(value = "/getProductIdByName", method = RequestMethod.POST)
//    public String getProductIdByName(@RequestBody ArgsBean argsBean) {
//    	 return new Integer(productService.getProductByName(argsBean).getId()).toString();
//    }
    
    // for record调用
    @RequestMapping(value = "/updateProductById", method = RequestMethod.POST)
    public String updateProductById(String productJsonString) {
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String productJsonString = (String)map.get("productJsonString");
        System.out.println("updateProductById:"+productJsonString);
    	Product product=JSONObject.parseObject(productJsonString, Product.class);//JSON字符串转对象
        return new Boolean(productService.updateProduct(product)).toString();
    }
}
