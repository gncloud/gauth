package io.swagger.dao;

import io.swagger.model.Client;
import io.swagger.model.Scope;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ScopeDao {

    @Autowired
    private SqlSession sqlSession;

    public List<Scope> selectScope(Client client) {
        return sqlSession.selectList("scope.selectScope");
    }

    public void insertScope(Scope scope) {
        sqlSession.insert("scope.insertScope", scope);
    }

    public void deleteScope(String scopeId) {
        sqlSession.delete("scope.deleteScope", scopeId);
    }

    public Scope findByScope(String scopeId) {
        return sqlSession.selectOne("scope.findByScope", scopeId);
    }

    public void updateScope(String scopeId, Scope scope) {
        sqlSession.update("scope.updateScope", scope);
    }




}
