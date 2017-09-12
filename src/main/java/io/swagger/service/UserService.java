package io.swagger.service;

import io.swagger.model.Token;
import io.swagger.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저 관리를 위한 service 인터페이스
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.08
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UserService {

    /*
     * 유저 조회
     * 아이디로 조회
     * */
    User findByUser(String userId);

    /*
     * 아이디 중복 확인
     * 아이디로 조회
     * */
    Integer isUserId(String userId);

    /*
     * 회원가입
     * */
    User signup(User user) throws Exception;

    /*
     * 회원탈퇴
     * */
    void deleteUser(User user);

    /*
     * 회원 정보 수정
     * */
    User updateUser(User user);

    /*
     * 토큰으로 회원 정보 조회
     */
    User fienByTokenToUserInfo(String token);
}