package com.dudu.mongo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dudu.mongo.domin.User;


/**
 * userDao接口定义
 * 
 * 作者： 段浩杰 2017年7月30日
 */
public interface UserDao {

    List<User> findAll();

    User getUser(Integer id);

    void update(User user);

    void insert(User user);

    void insertAll(List<User> users);

    void remove(Integer id);

    List<User> findByPage(User user, Pageable pageable);

}