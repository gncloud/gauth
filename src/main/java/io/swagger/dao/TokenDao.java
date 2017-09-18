package io.swagger.dao;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import io.swagger.model.User;
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
        return sqlSession.selectOne("token.findByToken", tokenId);
    }
    public Token findByToken(AuthenticationRequest user) {
        return sqlSession.selectOne("token.findByUserToToken", user);
    }

    public void deleteToken(String tokenId) {
        sqlSession.delete("token.deleteToken", tokenId);
    }

    public List<Token> selectTokens() {
        return sqlSession.selectList("token.selectTokens");
    }
}
