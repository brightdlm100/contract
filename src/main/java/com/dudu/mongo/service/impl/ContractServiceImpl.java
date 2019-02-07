package com.dudu.mongo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dudu.mongo.dao.ContractDao;
import com.dudu.mongo.dao.UserDao;
import com.dudu.mongo.domin.User;
import com.dudu.mongo.domin.合同;
import com.dudu.mongo.service.ContractService;
import com.dudu.mongo.service.UserService;

@Service("contractService")
public class ContractServiceImpl implements  ContractService{
	@Autowired
    private ContractDao contractDao;
	@Override
	public List<合同> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public  合同 getContract(String id) {
		return contractDao.getContract(id);
	}

	@Override
	public void update(合同 contract) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(合同 contract) {
		contractDao.insert(contract);
	}

	@Override
	public void insertAll(List<合同> contracts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findByPage(合同 contract, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


}
