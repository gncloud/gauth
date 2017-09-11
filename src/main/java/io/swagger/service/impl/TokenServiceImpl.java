package io.swagger.service.impl;

import io.swagger.dao.TokenDao;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.service.TokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService{

    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenDao tokenDao;

    @Value("${user.token.timeout}")
    private String tokenTimeout;


    /*
     * 로그인 토큰 생성
     */
    public Token createToken(User user){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
        // 토큰 생성 시간
        Date nowDate = Calendar.getInstance().getTime();
        String formatNowDate = dateFormat.format(nowDate);

        // 토큰 만료 시간, 기본값 60분
        int timeout = 60;
        if(tokenTimeout != null){
            timeout = Integer.parseInt(tokenTimeout);
        }
        // 생성시간 + 토근 유지 시간
        Calendar appendCalendar = Calendar.getInstance();
        appendCalendar.setTime(nowDate);
        appendCalendar.add(Calendar.MINUTE, timeout);
        String expireDate = dateFormat.format(appendCalendar.getTime());

        // 유저 아이디와 요청시간으로 랜덤값 생성
        String tokenSeed = user.getUserId() + formatNowDate;
        String userToken = DigestUtils.sha256Hex(tokenSeed);


        Token token = new Token();
        token.setUserId(user.getUserId());
        token.setClientId(user.getClientId());
        token.setTokenId(userToken);
        token.setCreateTime(formatNowDate);
        token.setExpireDate(expireDate);

        tokenDao.insertToekn(token);

        Token registerToken = findByToken(token.getTokenId());

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 조회
     */
    public Token findByToken(String tokenId){
        return tokenDao.findByToken(tokenId);
    }

    /*
     * 토큰 아이디로 토큰 정보 삭제
     */
    public void deleteToekn(String tokenId){
        tokenDao.deleteToken(tokenId);
    }




}
