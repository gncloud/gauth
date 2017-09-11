package io.swagger.service.impl;

import io.swagger.dao.ClientDao;
import io.swagger.dao.UserClientScopeDao;
import io.swagger.model.Client;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import io.swagger.service.ClientService;
import io.swagger.service.UserClientScopeService;
import io.swagger.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
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

    /*
     * 유저 클라이언트 등록
     */
    @Override
    public void insertUserClientScope(UserClientScope userClientScope){

    }

    /*
     * 유저 클라이언트 삭제
     */
    @Override
    public void deleteUserClientScope(UserClientScope userClientScope){

    }

    /*
     * 유저로 연결 조회
     */
    @Override
    public List<UserClientScope> selectUserMappingList(User user){
        return null;
    }

    /*
     * 클라이언트로 관계 조회
     */
    @Override
    public List<UserClientScope> selectUserMappingList(Client client){
        return null;
    }

    /*
     * 유저, 클라이언트로 관계 조회
     */
    @Override
    public List<UserClientScope> findUserMapping(UserClientScope userClientScope){
        return null;
    }

    /*
     * 클라이언트 등록된 관계 수
     */
    @Override
    public Integer findUserCount(String userId) {
        return userClientScopeDao.findUserCount(userId);
    }




}