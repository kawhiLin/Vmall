package com.user.service;

import com.user.dao.*;
import com.user.entity.User;
import com.user.entity.UserDetail;
import com.user.utils.HttpUtil;
import com.user.utils.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 14437 on 2017/3/1.
 */
@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDetailDao userDetailDao;
//    @Autowired
//    private ShoppingRecordDao shoppingRecordDao;
//    @Autowired
//    private ShoppingCarDao shoppingCarDao;
//    @Autowired
//    private EvaluationDao evaluationDao;

    @Override
    public User getUser(int id) {
        return userDao.getUser(id);
    }

    @Override
    public User getUser(String nameOrEmail) {
        return userDao.getUser(nameOrEmail);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    //推荐写法，具体业务逻辑放在Service实现方法里面
    @Override
    @Transactional
    public Response deleteUser(int id, String shoppingcarUrl, String recordUrl) {
        //判断此用户是否存在购买记录、评价记录、购物车记录，如果存在，则应该先删除对应的记录，否则后续删除会出错
        try {
        	  	
            //evaluationDao.deleteEvaluationByUser(id);
        	String url1 = shoppingcarUrl+"/deleteEvaluationByUser?userId=" + id;
     		String result1 = HttpUtil.sendGet(url1);
        	
            //shoppingCarDao.deleteShoppingCarByUser(id);
        	String url2 = shoppingcarUrl+"/deleteShoppingCarByUser?userId=" + id;
     		String result2 = HttpUtil.sendGet(url2);
     		
        	
            //shoppingRecordDao.deleteShoppingRecordByUser(id);
        	String url3 = recordUrl+"/deleteShoppingRecordByUser?userId=" + id;
     		String result3 = HttpUtil.sendGet(url3);
     		//System.out.println(result);

       
            
            userDetailDao.deleteUserDetail(id);
            userDao.deleteUser(id);
            return new Response(1, "删除成功", null);
        }catch (Exception e) {
            return new Response(0, "删除失败", null);
        }
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }
}
