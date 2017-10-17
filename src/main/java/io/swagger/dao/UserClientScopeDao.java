package io.swagger.dao;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Client;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserClientScopeDao {

    @Autowired
    private SqlSession sqlSession;


    public Integer findUserCount(int userCode) {
        return sqlSession.selectOne("userClientScope.findRelationCount", userCode);
    }

    public void insertRelation(UserClientScope userClientScope) {
        sqlSession.insert("userClientScope.insertRelation", userClientScope);
    }

    public List<UserClientScope> findRelation(UserClientScope userClientScope) {
        return sqlSession.selectList("userClientScope.findRelation", userClientScope);
    }

//    public void deleteUserClientScope(int userCode) {
//        sqlSession.delete("userClientScope.deleteRelationByUserCode", userCode);
//    }

    public List<UserClientScope> findUserCodeByRelations(User user) {
        return sqlSession.selectList("userClientScope.findUserCodeByRelations", user);
    }

    public List<UserClientScope> findUserCodeByRelations(Client client) {
        return sqlSession.selectList("userClientScope.fintClientMappingList", client);
    }

    public Integer isRelationCount(AuthenticationRequest user){
        return sqlSession.selectOne("userClientScope.isRelationCount", user);
    }

//    public List<String> findByScopeIdList(UserClientScope userClientScope) {
//        return sqlSession.selectList("userClientScope.findByScopeIdList", userClientScope);
//    }

//    public void deleteClientScope(UserClientScope userClientScope){
//        sqlSession.delete("deleteClientScope", userClientScope);
//    }

    public List<UserClientScope> findRelationByUsers(ArrayList<String> searchUser) {
        return sqlSession.selectList("userClientScope.findRelationByUsers", searchUser);
    }

    public void deleteUserClientScope(UserClientScope userClientScope){
        sqlSession.delete("userClientScope.deleteUserClientScope", userClientScope);
    }
}
