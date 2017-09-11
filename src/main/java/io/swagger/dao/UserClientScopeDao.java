package io.swagger.dao;

import io.swagger.model.Client;
import io.swagger.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserClientScopeDao {

    @Autowired
    private SqlSession sqlSession;


    public Integer findUserCount(String userId) {
        return sqlSession.selectOne("userClientScope.findUserCount", userId);
    }
}
