package io.swagger.dao;

import io.swagger.model.Client;
import io.swagger.model.Scope;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScopeDao {

    @Autowired
    private SqlSession sqlSession;

    public List<Scope> selectClientScopeList(Scope scope) {
        return sqlSession.selectList("scope.selectClientScopeList", scope);
    }

    public void insertScope(Scope scope) {
        sqlSession.insert("scope.insertScope", scope);
    }

    public void deleteScope(Scope scope) {
        sqlSession.delete("scope.deleteScope", scope);
    }

    public Scope findByScope(Scope scope) {
        return sqlSession.selectOne("scope.findByScope", scope);
    }

    public void updateScope(Scope scope) {
        sqlSession.update("scope.updateScope", scope);
    }

    public void updateDefaultN(Client client){
        sqlSession.update("scope.updateDefaultN", client);
    }

    public Scope findByDefailtScope(String clientId){
        return sqlSession.selectOne("scope.findByDefailtScope", clientId);
    }
}
