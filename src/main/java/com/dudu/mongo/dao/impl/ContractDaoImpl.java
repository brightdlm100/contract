package com.dudu.mongo.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.dudu.mongo.dao.ContractDao;
import com.dudu.mongo.dao.UserDao;
import com.dudu.mongo.domin.User;
import com.dudu.mongo.domin.合同;

/**
 * 接口实现
 * 
 * 作者： 段浩杰 2017年7月30日
 */
@Repository("contractDao")
public class ContractDaoImpl implements ContractDao {

    /**
     * 由springboot自动注入，默认配置会产生mongoTemplate这个bean
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查找全部
     */
    @Override
    public List<合同> findAll() {
        return mongoTemplate.findAll(合同.class);
    }

    /**
     * 根据id得到对象
     */
    @Override
    public 合同 getContract(String room) {
        return mongoTemplate.findOne(new Query(Criteria.where("房间").is(room)), 合同.class);
    }

    /**
     * 插入一个用户
     */
    @Override
    public void insert(合同 contract) {
        mongoTemplate.insert(contract);
    }

    /**
     * 根据id删除一个用户
     */
    @Override
    public void remove(String id) {
        Criteria criteria = Criteria.where("_id").is(id);  
        Query query = new Query(criteria);
        mongoTemplate.remove(query,合同.class);
    }

    /**
     * 分页查找
     * 
     * user代表过滤条件
     * 
     * pageable代表分页bean
     */
    @Override
    public List<合同> findByPage(合同 contract, Pageable pageable) {
        Query query = new Query();
        if (contract != null &&contract.房间!= null) {
            //模糊查询
            query = new Query(Criteria.where("name").regex("^" +contract.房间));
        }
        List<合同> list = mongoTemplate.find(query.with(pageable), 合同.class);
        return list;
    }

    /**
     * 根据id更新
     */
    @Override
    public void update(合同 user) {
        Criteria criteria = Criteria.where("id").is(user.房间);
        Query query = new Query(criteria);
        Update update = Update.update("name", user.房间).set("age", "124");
        mongoTemplate.updateMulti(query, update, User.class);
    }

    /**
     * 插入一个集合
     */
    @Override
    public void insertAll(List<合同> users) {
        mongoTemplate.insertAll(users);
    }

}