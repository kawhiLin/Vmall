package com.evaluation.controller;

import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.evaluation.entity.Evaluation;
import com.evaluation.service.EvaluationService;


import com.evaluation.utils.ArgsBean;
import com.evaluation.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class EvaluationController {
    @Resource
    private EvaluationService evaluationService;

    public static String userUrl;
    public static String productUrl;
    public static String shoppingcarUrl;
    public static String recordUrl;
    public static String evaluationUrl;

//    RestTemplate restTemplate = new RestTemplate();

    public EvaluationController() {
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
		System.out.println("url初始化：\n"+userUrl+"\n"+productUrl+"\n"+shoppingcarUrl+"\n"+recordUrl);
	}
    
    @RequestMapping(value = "/addShoppingEvaluation",method = RequestMethod.POST)
    public String addShoppingEvaluation(int userId, int productId, String content){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String userId = (String)map.get("userId");
//        String productId = (String)map.get("productId");
//        String content = (String)map.get("content");

        System.out.println("我添加了"+userId+" "+productId);
        String result = null;
        
        //调用record应用
       // String url1 = this.recordUrl+"/getUserProductRecord?userId="+userId+"&&productId="+productId;
        String url1 = this.recordUrl+"/getUserProductRecord";

        //String result1 = HttpUtil.sendGet(url1);
//		String result1 = restTemplate.postForObject(url1,argsBean,String.class);
        Map map = new HashMap();
        map.put("userId",String.valueOf(userId));
        map.put("productId",String.valueOf(productId));
        map.put("content",content);
        String result1 = HttpUtil.sendPost(url1,map);
        Map resMap = (Map) JSONObject.parse(result1);
        String resGetUserProductRecord = (String)resMap.get("result");

		if(resGetUserProductRecord.equals("true")){
            Evaluation evaluation = new Evaluation();
            evaluation.setUserId(Integer.valueOf(userId));
            evaluation.setProductId(Integer.valueOf(productId));
            Date date = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            evaluation.setTime(sf.format(date));
            evaluation.setContent(content);
            evaluationService.addEvaluation(evaluation);
            result = "success";
        }
        else{
            result="noneRecord";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return JSONObject.toJSONString(resultMap);
    }

    @RequestMapping(value = "/getShoppingEvaluations",method = RequestMethod.POST)
    public String getShoppingEvaluations(int productId){
//        Map map = (Map) JSONObject.parse(argsBean.getMapString());
//        //TODO 异常处理
//        String productId = (String)map.get("productId");

        List<Evaluation> evaluationList = evaluationService.getProductEvaluation(Integer.valueOf(productId));
        String evaluations = JSONArray.toJSONString(evaluationList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",evaluations);
        return JSONObject.toJSONString(resultMap);
    }
    
    
    //供user应用调用
    @RequestMapping(value = "/deleteEvaluationByUser",method = RequestMethod.POST)
    public String deleteEvaluationByUser(@RequestBody ArgsBean argsBean){
        Map map = (Map) JSONObject.parse(argsBean.getMapString());
        //TODO 异常处理
        String userId = (String)map.get("userId");

        String result = "false";
        if(evaluationService.deleteEvaluationByUser(Integer.valueOf(userId))){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return JSONObject.toJSONString(resultMap);
    }
    
    //供product应用调用
    @RequestMapping(value = "/deleteEvaluationByProduct",method = RequestMethod.POST)
    public String deleteEvaluationByProduct(@RequestBody ArgsBean argsBean){
        Map map = (Map) JSONObject.parse(argsBean.getMapString());
        //TODO 异常处理
        String productId = (String)map.get("productId");

        String result = "false";
        if(evaluationService.deleteEvaluationByProduct(Integer.valueOf(productId))){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return JSONObject.toJSONString(resultMap);
    }
}
