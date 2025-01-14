package com.web.controller;


import com.alibaba.fastjson.JSONObject;
import com.web.entity.User;
import com.web.utils.InitDB;
import com.web.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@Component
@EnableAutoConfiguration
public class PagesController {

    public static String userUrl;
    public static String productUrl;
    public static String shoppingcarUrl;
    public static String recordUrl;
    public static String evaluationUrl;


    @Autowired
    private RedisService redisService ;
    public PagesController() {
//        已有单独的Pod去初始化
//          InitDB.connmysql();
//        System.out.println("数据库初始化");

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
        System.out.println("url初始化：\n"  + userUrl + "\n" + productUrl + "\n" + shoppingcarUrl + "\n" + recordUrl + "\n"+ evaluationUrl);
    }

    private void sessionCheck(HttpSession  httpSession){
        //校验Session是否存在
        String sessionID = httpSession.getId();
        if(redisService.exists(sessionID)){
            JSONObject userJsonString = (JSONObject)redisService.get(sessionID);
            User user = JSONObject.toJavaObject(userJsonString, User.class);
            httpSession.setAttribute("currentUser",user);
            System.out.println("[INFO] Session existed！"+sessionID);
        }else{
            System.out.println("[INFO] Session is null");
        }


    }

    // 临时策略：当请求发送到新的web实例时，检查redis中是否存有该sessionID，有则将userinfo置入到httpsession中
    @RequestMapping(path = "/", method = RequestMethod.GET)
    String index(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "main";
    }

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    @ResponseBody
    String hello() {
        return "hello add order";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    String login() {
        return "login";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    String main(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "main";
    }

    @RequestMapping(value = "/control", method = RequestMethod.GET)
    String control(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "control";
    }

    @RequestMapping(value = "/amend_info", method = RequestMethod.GET)
    String amend_info(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "amend_info";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(String searchKeyWord, HttpSession httpSession) {
        sessionCheck(httpSession);
        httpSession.setAttribute("searchKeyWord",searchKeyWord);
        System.out.println("searchKeyWord="+ searchKeyWord);
        return "search";
    }

    @RequestMapping(value = "/product_detail", method = RequestMethod.GET)
    String product_detail(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "product_detail";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    String register() {
        return "register";
    }


    @RequestMapping(value = "/shopping_car", method = RequestMethod.GET)
    String shopping_car(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "shopping_car";
    }

    @RequestMapping(value = "/shopping_handle", method = RequestMethod.GET)
    String shopping_handle(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "shopping_handle";
    }
    @RequestMapping(value = "/shopping_record", method = RequestMethod.GET)
    String shopping_record(HttpSession httpSession) {
        sessionCheck(httpSession);
        return "shopping_record";
    }

    @RequestMapping(value = "/resetDb", method = RequestMethod.GET)
    @ResponseBody
    String  resetDb() {
        System.out.println("****resetDb****");
//		//初始化数据库
        String resultString = InitDB.connmysql();
        return resultString;
    }


}
