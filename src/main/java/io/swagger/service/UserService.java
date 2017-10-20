package io.swagger.service;

import io.swagger.api.ApiException;
import io.swagger.model.*;
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

    final String PENDING_STATUS = "pending";
    final String ACTIVE_STATUS = "active";

    /*
     * 유저 코드로 조회
     * */
    User getUser(int userCode);

    /*
     * 유저 아이디로 조회
     * */
    User getUser(String userId);

    /*
     * 유저 아이디, 비밀번호로 조회
     * */
    User getUser(AuthenticationRequest authenticationRequest);

    /*
     * 유저 아이디로 조회
     * */
    Integer isUserIdCount(String userId);

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
    User findTokenByUser(String token);

    /*
     * 유저 전체 조회
     */
    List<User> findByUsers(Map<String, String> search);

    /*
     * 대기 유저 등록
     */
    public PendingUserResponse insertPendingUser(PendingUserRequest pendingUserRequest) throws Exception;

    /*
     * activateKey로 대기 유저 조회
     */
//    PendingUserResponse findActivateKeyByPendUser(String activateKey);

    /*
     * activateKey 활성화
     */
    PendingUserResponse updatePendUserActive(String activateKey) throws ApiException;


    /*
     * 강제 회원삭제
     */
    void deleteUser(int userCode, String client);

    /*
     * 유저 수
     */
    int selectUserCount(Map<String, String> search);

    /*
     * 대기 유저 전체 조회
     */
    List<PendingUserResponse> findByPendingUserInfoList(Token adminToken);

    /*
     * 대기 유저 전체 삭제
     */
    void deleteAllPendUser();

    /*
     * 대기 유저 삭제 target : email
     */
    void deleteEmailByPendUser(String email);

    /*
     * 대기 유저 삭제 target : activateKey
     */
    void deletePendUserActivateKey(String activateKey);

}