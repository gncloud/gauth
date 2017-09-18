package io.swagger.dao;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Client;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
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

    public void insertUserClientScope(UserClientScope userClientScope) {
        sqlSession.insert("userClientScope.insertUserClientScope", userClientScope);
    }

    public UserClientScope findByUserClientScope(UserClientScope userClientScope) {
        return sqlSession.selectOne("userClientScope.findByUserClientScope", userClientScope);
    }

    public void deleteUserClientScope(UserClientScope userClientScope) {
        sqlSession.delete("userClientScope.deleteUserClientScope", userClientScope);
    }


    public List<UserClientScope> fintUserMappingList(User user) {
        return sqlSession.selectList("userClientScope.fintUserMappingList", user);
    }

    public List<UserClientScope> fintClientMappingList(Client client) {
        return sqlSession.selectList("userClientScope.fintClientMappingList", client);
    }

    public Integer isUserClientScope(AuthenticationRequest user){
        return sqlSession.selectOne("userClientScope.isUserClientScoe", user);
    }

    public List<String> findByScopeIdList(UserClientScope userClientScope) {
        return sqlSession.selectList("userClientScope.findByScopeIdList", userClientScope);
    }
}
