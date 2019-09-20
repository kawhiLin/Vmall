package com.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.user.entity.User;
import com.user.service.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        this.userUrl = "http://user:8081";
        this.productUrl = "http://product:8082";
        this.shoppingcarUrl = "http://shoppingcart:8083";
        this.recordUrl = "http://order:8084";
        this.evaluationUrl = "http://evaluation:8085";
        System.out.println("url初始化：\n" + userUrl + "\n" + productUrl + "\n" + shoppingcarUrl + "\n" + recordUrl + "\n"+ evaluationUrl);
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Map<String, Object> doLogin(String userNameOrEmail, String password) {
        return userService.doLogin(userNameOrEmail, password);
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public Map<String, Object> doRegister(String userName, String email, String nickName, String password, String phoneNumber, int sex, String birthday, String postNumber, String address) {
        System.out.println("我接收到了注册用户请求");
        return userService.doRegister(userName, email, nickName, password, phoneNumber, sex, birthday, postNumber, address);
    }

    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    public Map<String, Object> doUpdate(String userName, String email, String nickName, String password,
                                        String phoneNumber, int sex, String birthday, String postNumber, String address) {
        System.out.println("我接收到了更新用户请求");
        return userService.doUpdate(userName, email, nickName, password,
                phoneNumber, sex, birthday, postNumber, address);
    }

    @RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
//    @ResponseBody
    public Map<String, Object> getAllUser() {
        System.out.println("我接收到了获取用户请求");
        return userService.getAllUser();
    }
//
//    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
//    public String deleteUser(@RequestBody ArgsBean argsBean) {
//        return  JSONObject.toJSONString(userService.deleteUser(argsBean));
//    }

    @RequestMapping(value = "/getUserAddressAndPhoneNumber", method = RequestMethod.POST)
    public Map<String, Object> getUserAddressAndPhoneNumber(int id) {
        return userService.getUserAddressAndPhoneNumber(id);
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    public Map<String, Object> getUserById(int id){
        System.out.println("我接收到了getUserById请求");
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/getUserByName", method = RequestMethod.POST)
    public User getUserByName(@RequestBody String name){
        System.out.println("我接收到了getUserByName请求");
        return userService.getUserByName(name);
    }


    @RequestMapping(value = "/getUserDetailById", method = RequestMethod.POST)
    public String getUserDetailById(int id) {
        return JSONObject.toJSONString(userService.getUserDetailById(id));
    }
}
