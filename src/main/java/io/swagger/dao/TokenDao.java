package io.swagger.dao;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TokenDao {

    @Autowired
    private SqlSession sqlSession;

    public void insertToken(Token token) {
        sqlSession.insert("token.insertToken", token);
    }

    public Token getToken(String tokenId) {
        return sqlSession.selectOne("token.getToken", tokenId);
    }
    public Token findUserByToken(AuthenticationRequest user) {
        return sqlSession.selectOne("token.findUserByToken", user);
    }

    public void deleteToken(String tokenId) {
        sqlSession.delete("token.deleteToken", tokenId);
    }

    public List<Token> selectTokens() {
        return sqlSession.selectList("token.selectTokens");
    }

    public void deleteTokenByUserCode(int userCode) {
        sqlSession.delete("token.deleteTokenByUserCode", userCode);
    }

    public Token findByAdminToken(Token token) {
        return sqlSession.selectOne("token.findByAdminToken", token);
    }

    public void deleteClientIdByToken(String clientId) {
        sqlSession.delete("token.deleteClientIdByToken", clientId);
    }
}
