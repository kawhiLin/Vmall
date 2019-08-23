package com.shoppingcar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shoppingcar.dao.EvaluationDao;
import com.shoppingcar.entity.Product;
import com.shoppingcar.entity.User;
import com.shoppingcar.entity.Evaluation;
import com.shoppingcar.service.EvaluationService;
import com.shoppingcar.utils.HttpUtil;

import org.springframework.beans.factory.annotation.Autowired;
//import com.shoppingcar.service.ShoppingRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/7.
 */
@Controller
public class EvaluationController {
    @Resource
    private EvaluationService evaluationService;
    
	@Autowired
	private EvaluationDao evaluationDao;
   // @Resource
    //private ShoppingRecordService shoppingRecordService;
    private String userUrl;
    private String productUrl;
    private String shoppingcarUrl;
    private String recordUrl;
    
    public EvaluationController() {
		// TODO Auto-generated constructor stub
		this.userUrl = "http://user-vmall:8080/user";
		this.productUrl = "http://product-vmall:8080/product";
		this.shoppingcarUrl = "http://shoppingcar-vmall:8080/shoppingcar";
		this.recordUrl = "http://record-vmall:8080/record";
		System.out.println("url初始化：\n"+userUrl+"\n"+productUrl+"\n"+shoppingcarUrl+"\n"+recordUrl);
	}
    
    @RequestMapping(value = "/addShoppingEvaluation",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addShoppingEvaluation(int userId, int productId, String content){
        System.out.println("我添加了"+userId+" "+productId);
        String result = null;
        
        //调用record应用
        String url1 = this.recordUrl+"/getUserProductRecord?userId="+userId+"&&productId="+productId;
		String result1 = HttpUtil.sendGet(url1);
		
        //if(shoppingRecordService.getUserProductRecord(userId,productId)){
		if(result1.equals("true")){
            Evaluation evaluation = new Evaluation();
            evaluation.setUserId(userId);
            evaluation.setProductId(productId);
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
        return resultMap;
    }

    @RequestMapping(value = "/getShoppingEvaluations",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getShoppingEvaluations(int productId){
    	
//    	//Session传递
//        //userId、userNickName
//		System.out.println("-------currentUserId: "+currentUserId);
//        User user = new User();
//        user.setId(currentUserId);
//        user.setNickName(currentUserNickName);
//		httpSession.setAttribute("currentUser", user);
		
        List<Evaluation> evaluationList = evaluationService.getProductEvaluation(productId);
        String evaluations = JSONArray.toJSONString(evaluationList);
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",evaluations);
        return resultMap;
    }
    
    
    //供user应用调用
    @RequestMapping(value = "/deleteEvaluationByUser",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deleteEvaluationByUser(int userId){
        String result = "false";
        if(evaluationDao.deleteEvaluationByUser(userId)){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return resultMap;
    }
    
    //供product应用调用
    @RequestMapping(value = "/deleteEvaluationByProduct",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> deleteEvaluationByProduct(int productId){
        String result = "false";
        if(evaluationDao.deleteEvaluationByProduct(productId)){
            result = "true";
        }
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",result);
        return resultMap;
    }
}
