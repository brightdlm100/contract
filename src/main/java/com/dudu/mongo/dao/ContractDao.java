package com.dudu.mongo.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dudu.mongo.domin.User;
import com.dudu.mongo.domin.合同;


/**
 * userDao接口定义
 * 
 * 作者： 段浩杰 2017年7月30日
 */
public interface ContractDao {

    List<合同> findAll();

    合同 getContract(String id);

    void update(合同 contract);

    void insert(合同 contract);

    void insertAll(List<合同> contracts);

    void remove(String id);

    List<合同> findByPage(合同 contract, Pageable pageable);

}