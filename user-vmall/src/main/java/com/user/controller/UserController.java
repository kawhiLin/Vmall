package com.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.user.service.UserService;
import com.user.utils.ArgsBean;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@EnableAutoConfiguration
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public static String userUrl;
    public static String productUrl;
    public static String shoppingcarUrl;
    public static String recordUrl;
    public static String evaluationUrl;

    public UserController() {
//        this.userUrl = "http://127.0.0.1:8081";
//        this.productUrl = "http://127.0.0.1:8082";
//        this.shoppingcarUrl = "http://127.0.0.1:8083";
//        this.recordUrl = "http://127.0.0.1:8084";
//        this.evaluationUrl = "http://127.0.0.1:8085";

        this.userUrl = "http://user:8081";
        this.productUrl = "http://product:8082";
        this.shoppingcarUrl = "http://shoppingcar:8083";
        this.recordUrl = "http://order:8084";
        this.evaluationUrl = "http://evaluation:8085";
        System.out.println("url初始化：\n" + userUrl + "\n" + productUrl + "\n" + shoppingcarUrl + "\n" + recordUrl + "\n"+ evaluationUrl);
    }

    @RequestMapping(value = "/doLogin", produces = { "application/json" }, method = RequestMethod.POST)
    public String doLogin(@RequestBody ArgsBean argsBean) {
        return JSONObject.toJSONString(userService.doLogin(argsBean));
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String  doRegister(@RequestBody ArgsBean argsBean) {
        System.out.println("我接收到了注册用户请求");
        return JSONObject.toJSONString(userService.doRegister(argsBean));
    }

    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public String doUpdate(@RequestBody ArgsBean argsBean) {
        System.out.println("我接收到了更新用户请求");
        return JSONObject.toJSONString(userService.doUpdate(argsBean));
    }

    @RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
//    @ResponseBody
    public String getAllUser() {
        System.out.println("我接收到了获取用户请求");
        return JSONObject.toJSONString(userService.getAllUser());
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestBody ArgsBean argsBean) {
        return  JSONObject.toJSONString(userService.deleteUser(argsBean));
    }

    @RequestMapping(value = "/getUserAddressAndPhoneNumber", method = RequestMethod.POST)
    public String getUserAddressAndPhoneNumber(@RequestBody ArgsBean argsBean) {
        return JSONObject.toJSONString(userService.getUserAddressAndPhoneNumber(argsBean));
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    public String getUserById(@RequestBody ArgsBean argsBean){
        System.out.println("我接收到了getUserById请求");
        return JSONObject.toJSONString(userService.getUserById(argsBean));
    }

    @RequestMapping(value = "/getUserByName", method = RequestMethod.POST)
    public String getUserByName(@RequestBody String name){
        System.out.println("我接收到了getUserByName请求");
        return JSONObject.toJSONString(userService.getUserByName(name));
    }


    @RequestMapping(value = "/getUserDetailById", method = RequestMethod.POST)
    public String getUserDetailById(@RequestBody ArgsBean argsBean) {
        return JSONObject.toJSONString(userService.getUserDetailById(argsBean));
    }
}
