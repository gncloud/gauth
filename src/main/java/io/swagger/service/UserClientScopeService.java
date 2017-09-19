package io.swagger.service;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Client;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 유저와 클라이언트 연결을 관리
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.11
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface UserClientScopeService {


    /*
     * 유저 클라이언트 등록
     */
    List<UserClientScope> insertUserClientScope(UserClientScope userClientScope);

    /*
     * 유저 클라이언트 삭제
     */
    void deleteUserClientScope(UserClientScope userClientScope);

    /*
     * 유저 삭제
     */
    void deleteUser(String userId);

    /*
     * 유저로 관계 조회
     */
    List<UserClientScope> selectUserMappingList(User user);

    /*
     * 클라이언트로 관계 조회
     */
    List<UserClientScope> selectClientMappingList(Client client);

    /*
     * 클라이언트 등록된 관계 수
     */
    Integer findUserCount(String userId);

    /*
     * 유저가 클라이언트 등록 여부를 확인
     */
    boolean isUserClientScope(AuthenticationRequest user);

    /*
     * 유저와 클라이언트 ScopeId 정보 조회
     */
    List<String> findByScopeIdList(UserClientScope userClientScope);

}