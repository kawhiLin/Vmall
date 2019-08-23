package com.user.service;

import com.user.entity.User;
import com.user.utils.Response;

import java.util.List;

/**
 * Created by 14437 on 2017/3/1.
 */
public interface UserService {
    User getUser(int id);

    User getUser(String nameOrEmail);

    void addUser(User user);

    //推荐写法
    Response deleteUser(int id, String shoppingcarUrl, String recordUrl);

    boolean updateUser(User user);

    List<User> getAllUser();
}
