package io.swagger.service.impl;

import io.swagger.dao.UserClientScopeDao;
import io.swagger.model.*;
import io.swagger.service.ScopeService;
import io.swagger.service.UserClientScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 유저와 클라이언트 연결을 관리
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.11
 */
@Service
public class UserClientScopeImpl implements UserClientScopeService {

    @Autowired
    private UserClientScopeDao userClientScopeDao;

    @Autowired
    private ScopeService scopeService;
    /*
     * 유저 클라이언트 등록
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<UserClientScope> insertUserClientScope(UserClientScope userClientScope){

        // 기존 정보 삭제
        deleteUserClientScope(userClientScope);

        List<Scope> scopesList = scopeService.findDefaultByScopes(userClientScope.getClientId());

        int scopeSize = scopesList.size();
        for(int i=0; i < scopeSize; i++){
            UserClientScope target = new UserClientScope();
            target.setClientId(userClientScope.getClientId());
            target.setUserCode(userClientScope.getUserCode());
            target.setScopeId(scopesList.get(i).getScopeId());

            // 스코프 만큼 추가
            userClientScopeDao.insertRelation(target);
        }

        if(scopeSize == 0){
            userClientScopeDao.insertRelation(userClientScope);
        }

        return userClientScopeDao.findRelation(userClientScope);
    }

    /*
     * 유저 스코프 등록
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<UserClientScope> insertUserScope(UserClientScope userClientScope) {
        userClientScopeDao.insertRelation(userClientScope);
        return userClientScopeDao.findRelation(userClientScope);
    }

    /*
     * 유저 클라이언트 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteUserClientScope(UserClientScope userClientScope){
        userClientScopeDao.deleteUserClientScope(userClientScope);
    }

    /*
     * 클라이언트 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteClient(String clientId){
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setClientId(clientId);
        userClientScopeDao.deleteUserClientScope(userClientScope);
    }


//    @Override
//    public void deleteUser(String userId) {
//        userClientScopeDao.deleteUser(userId);
//    }

    /*
     * 유저로 연결 조회
     */
    @Override
    public List<UserClientScope> selectUserMappingList(User user){
        return userClientScopeDao.findUserCodeByRelations(user);
    }

    /*
     * 클라이언트로 관계 조회
     */
//    @Override
//    public List<UserClientScope> selectClientMappingList(Client client){
//        return userClientScopeDao.fintClientMappingList(client);
//    }


    /*
     * 클라이언트 등록된 관계 수
     */
    @Override
    public Integer findUserCount(int userCode) {
        return userClientScopeDao.findUserCount(userCode);
    }

    /*
     * 유저가 클라이언트 등록 여부를 확인
     */
    @Override
    public boolean isUserClientScope(AuthenticationRequest user) {
        Integer userCnt = userClientScopeDao.isRelationCount(user);
        return (userCnt != null && userCnt >= 1);
    }

//    /*
//     * 유저와 클라이언트 ScopeId 정보 조회
//     */
//    @Override
//    public List<String> findByScopeIdList(UserClientScope userClientScope) {
//        return userClientScopeDao.findByScopeIdList(userClientScope);
//    }

    /*
     * 클라이언트의 스코프 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteClientScope(UserClientScope userClientScope) {
        userClientScopeDao.deleteUserClientScope(userClientScope);
    }

    /*
     * 전체 조회시 사용되는 클라이언트 정보 조회
     */
    @Override
    public List<UserClientScope> findByUserSearchList(ArrayList<String> searchUser) {
        return userClientScopeDao.findRelationByUsers(searchUser);
    }

    /*
     * 유저 스코프 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteUserScope(UserClientScope userClientScope) {
        userClientScopeDao.deleteUserClientScope(userClientScope);
    }


}