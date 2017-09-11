package io.swagger.service;

import io.swagger.model.Token;
import io.swagger.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface TokenService {

    /*
     * 로그인 토큰 생성
     */
    Token createToken(User user);

    /*
     * 토큰 아이디로 토큰 정보 조회
     */
    Token findByToken(String tokenId);

    /*
     * 토큰 아이디로 토큰 정보 삭제
     */
    void deleteToekn(String tokenId);
}
