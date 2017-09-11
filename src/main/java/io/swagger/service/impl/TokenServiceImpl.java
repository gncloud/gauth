package io.swagger.service.impl;

import io.swagger.dao.TokenDao;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenServiceImpl implements TokenService{

    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenDao tokenDao;

    @Override
    public Token insertToken(User user) {
        Token newToken = new Token();
        tokenDao.insertToekn(newToken);
        Token registerToken = findByToken(newToken.getTokenId());
        return registerToken;
    }

    @Override
    public Token findByToken(String tokenId) {
        return tokenDao.findByToken(tokenId);
    }

    @Override
    public void deleteToekn(String tokenId) {
        tokenDao.deleteToken(tokenId);
    }




}
