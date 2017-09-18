package io.swagger.service.impl;

import io.swagger.dao.UserDao;
import io.swagger.model.PendingUserRequest;
import io.swagger.model.PendingUserResponse;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import io.swagger.service.ScopeService;
import io.swagger.service.UserClientScopeService;
import io.swagger.service.UserService;
import io.swagger.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Value("${server.host}")
    private String serverHost;



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
//        if (userId == null || "".equals(userId)) {
//            throw new Exception("no id");
//        }
//
//        String password = user.getPassword();
//        if (password == null || "".equals(password)) {
//            throw new Exception("no password");
//        }

//        if (!requiredValueCheck(user) || isUserCount != 0) {
//            throw new Exception("user requred invalid");
//        }

        // 회원 정보 등록
        userDao.insertUser(user);

        // 클라이언트의 default scope 조회
//        Scope registerScope = scopeService.findByDefailtScope(user.getClientId());

        // 처음 회원 가입한 클라이언트 기본 등록
        UserClientScope userClientScope = new UserClientScope();
//        userClientScope.setClientId(user.getClientId());
//        userClientScope.setUserId(user.getUserId());
//        if(registerScope != null){
//            userClientScope.setScopeId(registerScope.getScopeId());
//        }

//        userClientScopeService.insertUserClientScope(userClientScope);

        // 등록된 DB 유저 가져오기
//        User registerUser = findByUser(user.getUserId());
//        return registerUser;
        return null;
    }

    /*
     * 회원탈퇴
     * */
    @Override
    public void deleteUser(User user) {

        // 토큰 정보로 접근 하였을 경우 유저 조회
//        String token = user.getTokenId();
        User targetUser = new User();

//        if(token != null && !"".equals(token)){
//            targetUser = fienByTokenToUserInfo(token);
//        }else{
//            targetUser = user;
//        }


        // 유저,클라이언트 관계 데이터 삭제
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setUserId(targetUser.getUserId());
//        userClientScope.setClientId(targetUser.getClientId());

        userClientScopeService.deleteUserClientScope(userClientScope);

        // 유저와 클라이언트 마지막 확인 후 마지막 이면 유저 정보 삭제 조회
        Integer userClientCount = userClientScopeService.findUserCount(targetUser.getUserId());

        // 유저와 클라이언트 관계가 마지막일 경우 회원 정보 삭제
        if(userClientCount == 0){
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
//        if(user.getTokenId() != null && !"".equals(user.getTokenId())){
//            targetUser = userDao.fienByTokenToUserInfo(user.getTokenId());
//            user.setUserId(targetUser.getUserId());
//        }

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
     * 유저 전체 조회
     */
    @Override
    public List<User> findByUsers(String search) {
        return userDao.findByUsers(search);
    }

    /*
     * 회원 가입 대기 유저 정보 등록
     */
    @Override
    public PendingUserResponse insertPendingUser(PendingUserRequest pendingUserRequest) throws Exception {
        PendingUserResponse pendingUserResponse = new PendingUserResponse();

        String email = pendingUserRequest.getEmail();
        String clientId = pendingUserRequest.getClientId();

        if(email == null || "".equals(email)){
            throw new Exception("email field");
        }else if(clientId == null || "".equals(clientId)){
            throw new Exception("clientId field");
        }

        String activateKey = RandomUtil.randomString(32);
        String retryUrl = serverHost + "?activateKey=" + activateKey;

        pendingUserResponse.setEmail(email);
        pendingUserResponse.setClientId(clientId);
        pendingUserResponse.setActivateKey(activateKey);
        pendingUserResponse.setRetryUrl(retryUrl);

        userDao.insertPendingUser(pendingUserResponse);

        return findByPendingUserInfo(email, activateKey);
    }

    /*
     * 회원 가입 대기 유저 조회
     */
    @Override
    public PendingUserResponse findByPendingUserInfo(String email, String activateKey) {
        PendingUserResponse pendingUserResponse = new PendingUserResponse();
        pendingUserResponse.setActivateKey(activateKey);
        pendingUserResponse.setEmail(email);
        return userDao.findByPendingUser(pendingUserResponse);
    }

    /*
     * 강한 회원탈퇴
     * */
    @Override
    public void deleteUser(String userId) {


    }

    /*
     * 회원 가입 대기 유저 활성화
     */
    @Override
    public void pendingUserActivate(String activateKey) {



    }


}