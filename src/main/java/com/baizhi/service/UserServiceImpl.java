package com.baizhi.service;

import com.baizhi.dao.UserDAO;
import com.baizhi.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: timor
 * @date: 2020/7/15 22:35
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {

        return userDAO.login(user);
    }
}
