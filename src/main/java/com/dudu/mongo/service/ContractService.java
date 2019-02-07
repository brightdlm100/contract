package com.dudu.mongo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.dudu.mongo.domin.User;
import com.dudu.mongo.domin.合同;


public interface ContractService {

    List<合同> findAll();

    合同 getContract(String id);

    void update(合同 contract);

    void insert(合同 contract);

    void insertAll(List<合同> contracts);

    void remove(String id);

    List<User> findByPage(合同 contract,Pageable pageable);
}