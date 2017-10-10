package io.swagger.service;

import io.swagger.model.PendingUserRequest;
import io.swagger.model.PendingUserResponse;
import io.swagger.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    User signup(User user, String activateKey, String clientId) throws Exception;

    /*
     * 회원 정보 수정
     * */
    User updateUser(User user);

    /*
     * 토큰으로 회원 정보 조회
     */
    User fienByTokenToUserInfo(String token);

    /*
     * 유저 전체 조회
     */
    List<User> findByUsers(Map<String, String> search);

    /*
     * 회원 가입 대기 유저 정보 등록
     */
    public PendingUserResponse insertPendingUser(PendingUserRequest pendingUserRequest) throws Exception;

    /*
     * 회원 가입 대기 유저 조회
     */
    public PendingUserResponse findByPendingUserInfo(String activateKey);

    /*
     * 강제 회원삭제
     */
    void deleteUser(String userId, String client);

    /*
     * 대기회원상태
     */
    PendingUserResponse updatePendingStatus(String activateKey);

    /*
     * 유저 수
     */
    int selectUserCount(Map<String, String> search);

    /*
     * 대기 유저 전체 조회
     */
    List<PendingUserResponse> findByPendingUserInfoList();

    /*
     * 대기 유저 전체 삭제
     */
    void deleteAllPendUser();

    /*
     * 대기 유저 삭제
     */
    void deletePendingUser(String email);

}