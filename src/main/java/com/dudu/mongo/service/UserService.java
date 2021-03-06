package com.dudu.mongo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dudu.mongo.domin.User;


public interface UserService {

    List<User> findAll();

    User getUser(Integer id);

    void update(User user);

    void insert(User user);

    void insertAll(List<User> users);

    void remove(Integer id);

    List<User> findByPage(User user,Pageable pageable);
}