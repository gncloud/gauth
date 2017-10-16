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

    public void insertToekn(Token token) {
        sqlSession.insert("token.insertToken", token);
    }

    public Token findByToken(String tokenId) {
        return sqlSession.selectOne("token.getToken", tokenId);
    }
    public Token findByToken(AuthenticationRequest user) {
        return sqlSession.selectOne("token.findTokenByUser", user);
    }

    public void deleteToken(String tokenId) {
        sqlSession.delete("token.deleteToken", tokenId);
    }

    public List<Token> selectTokens() {
        return sqlSession.selectList("token.selectTokens");
    }

    public void deleteClientToken(AuthenticationRequest user) {
        sqlSession.delete("token.deleteClientToken", user);
    }

    public Token findByAdminToken(Token token) {
        return sqlSession.selectOne("token.findByAdminToken", token);
    }

    public void deleteClient(String clientId) {
        sqlSession.delete("token.deleteClient", clientId);
    }
}
