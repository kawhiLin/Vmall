package com.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.entity.User;
import com.web.utils.ArgsBean;
import com.web.utils.HttpUtil;
import com.web.utils.RedisService;
import com.web.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
//@RestSchema(schemaId = "userController")
@RequestMapping("/")
@EnableAutoConfiguration
public class UserController {

//    private static RestTemplate restTemplate =  new RestTemplate();

    @Autowired
    private RedisService redisService ;

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(String userName, String email, String nickName, String password,
                                          String phoneNumber, int sex, String birthday, String postNumber, String address) {

        String url =  PagesController.userUrl+"/doRegister";
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("email", email);
        map.put("nickName", nickName);
        map.put("password", password);
        map.put("phoneNumber", phoneNumber);
        map.put("sex", String.valueOf(sex));
        map.put("birthday", birthday);
        map.put("postNumber", postNumber);
        map.put("address", address);


//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);

        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        return resultMap;
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(String userNameOrEmail, String password, HttpSession httpSession) {
        String url = PagesController.userUrl + "/doLogin";
        Map<String,String> map = new HashMap<String,String>();
        map.put("userNameOrEmail",userNameOrEmail);
        map.put("password",password);

//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);



        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        String result = (String) resultMap.get("result");
        if (result.equals("success")) {
            System.out.println("here to add session info");
            JSONObject userJsonString = (JSONObject) resultMap.get("currentUser");
            User user = JSONObject.toJavaObject(userJsonString, User.class);
            System.out.println("currentUser=" + userJsonString);
            httpSession.setAttribute("currentUser", user);

            // 存入sessionID：userInfo到redis
            // 临时策略：当请求发送到新的web实例时，检查redis中是否存有该sessionID，有则将userinfo置入到httpsession中
            System.out.println("SESSIONID="+httpSession.getId());
            // user序列化
            redisService.set(httpSession.getId(),userJsonString);
        }
        System.out.println("我接收到了登录请求" + userNameOrEmail + " " + password);
        return resultMap;
    }
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doUpdate(String userName, String email, String nickName, String password,
                                        String phoneNumber, int sex, String birthday, String postNumber, String address) {
        String url = PagesController.userUrl + "/doUpdate";
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("email", email);
        map.put("nickName", nickName);
        map.put("password", password);
        map.put("phoneNumber", phoneNumber);
        map.put("sex", String.valueOf(sex));
        map.put("birthday", birthday);
        map.put("postNumber", postNumber);
        map.put("address", address);

//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);


        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        return resultMap;
    }

    @RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAllUser() {
        String url = PagesController.userUrl + "/getAllUser";

//        String res = restTemplate.postForObject(url,null,String.class);
        String res = HttpUtil.sendPost(url);

        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        return resultMap;
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public Response deleteUser(int id) {
        String url = PagesController.userUrl + "/deleteUser";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));

//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);


        JSONObject userJsonString = JSONObject.parseObject(res);
        Response response = JSONObject.toJavaObject(userJsonString, Response.class);
        return response;
    }

    @RequestMapping(value = "/getUserAddressAndPhoneNumber", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserAddressAndPhoneNumber(int id) {
        String url = PagesController.userUrl + "/getUserAddressAndPhoneNumber";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));

//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);

        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        return resultMap;
    }

    @RequestMapping(value = "/doLogout")
    public String doLogout(HttpSession httpSession) {
        // TODO 删除redis中记录
        httpSession.setAttribute("currentUser", null);
        return "redirect:login";
    }

    @RequestMapping(value = "/getUserById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserById(int id) {
        String url = PagesController.userUrl + "/getUserById";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));

//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);

        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        return resultMap;
    }

    @RequestMapping(value = "/getUserDetailById", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUserDetailById(int id) {
        String url = PagesController.userUrl + "/getUserDetailById";
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));

//        ArgsBean argsBean = new ArgsBean();
//        argsBean.setMapString(JSONObject.toJSONString(map));
//        String res = restTemplate.postForObject(url,argsBean,String.class);
        String res = HttpUtil.sendPost(url, map);
        System.out.println("----res:\n" + res);
        Map resultMap = (Map) JSON.parse(res);
        return resultMap;
    }
}
