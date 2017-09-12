package io.swagger.service.impl;

import io.swagger.dao.UserDao;
import io.swagger.model.*;
import io.swagger.service.ScopeService;
import io.swagger.service.UserClientScopeService;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserClientScopeService userClientScopeService;

    @Autowired
    private ScopeService scopeService;

    /*
     * 유저 조회
     * 유저아이디로 조회
     * */
    @Override
    public User findByUser(String userId) {
        return userDao.findByUser(userId);
    }

    /*
     * 아이디 중복 확인
     * 아이디로 조회
     * */
    @Override
    public Integer isUserId(String userId) {
        return userDao.isUserId(userId);
    }

    /*
     * 회원가입
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User signup(User user) throws Exception {

        //필수 값 체크
        Integer isUserCount = isUserId(user.getUserId());
        if (!requiredValueCheck(user) || isUserCount != 0) {
            throw new Exception("user requred invalid");
        }

        // 회원 정보 등록
        userDao.insertUser(user);

        // 클라이언트의 scope 조회
        Scope scope = new Scope();
        scope.setClientId(user.getClientId());
        Scope registerScope = scopeService.selectScope(scope);
        String scopeId = new String();
        if(registerScope != null && !"".equals(registerScope.getScopeId())){
            scopeId = registerScope.getScopeId();
        }

        // 처음 회원 가입한 클라이언트 기본 등록
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setClientId(user.getClientId());
        userClientScope.setUserId(user.getUserId());
        userClientScope.setScopeId(scopeId);
        userClientScopeService.insertUserClientScope(userClientScope);

        // 등록된 DB 유저 가져오기
        return findByUser(user.getUserId());
    }

    /*
     * 회원탈퇴
     * */
    @Override
    public void deleteUser(User user) {


        String token = user.getTokenId();
        User targetUser = new User();
        if(token != null && !"".equals(token)){
            targetUser = fienByTokenToUserInfo(token);
        }else{
            targetUser = user;
        }

        // 유저,클라이언트 관계 데이터 삭제
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setUserId(targetUser.getUserId());
        userClientScope.setClientId(targetUser.getClientId());

        userClientScopeService.deleteUserClientScope(userClientScope);

        // 클라이언트 관계 갯수 조회
        Integer userClientCount = userClientScopeService.findUserCount(targetUser.getUserId());
        // 유저와 클라이언트 관계가 마지막일 경우 회원 정보 삭제
        if(userClientCount <= 1){
            userDao.deleteUser(user);
        }

    }

    /*
     * 유저 수정
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User updateUser(User user) {

        User targetUser = new User();

        // 토큰으로 왔을 시 토큰으로 아이디 조회
        if(user.getTokenId() != null && !"".equals(user.getTokenId())){
            targetUser = userDao.fienByTokenToUserInfo(user.getTokenId());
            user.setUserId(targetUser.getUserId());
        }

        userDao.updateUser(user);
        return userDao.findByUser(user.getUserId());
    }
    /*
     * 토큰으로 회원 정보 조회
     */
    @Override
    public User fienByTokenToUserInfo(String token) {
        return userDao.fienByTokenToUserInfo(token);
    }


    /*
     * 필수값 유효성 체크
     */
    private boolean requiredValueCheck(User user) {
        boolean isValid = false;
        try {
            String userId = user.getUserId();
            if (userId == null || "".equals(userId)) {
                throw new Exception("no id");
            }

            String password = user.getPassword();
            if (password == null || "".equals(password)) {
                throw new Exception("no password");
            }

            isValid = true;
        } catch (Exception e) {
            logger.debug("requiredValue ::: {}", e.getMessage());
        }
        return isValid;
    }

}