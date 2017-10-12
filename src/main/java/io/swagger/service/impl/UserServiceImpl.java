package io.swagger.service.impl;

import io.swagger.api.ApiException;
import io.swagger.dao.UserDao;
import io.swagger.model.PendingUserRequest;
import io.swagger.model.PendingUserResponse;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import io.swagger.service.*;
import io.swagger.util.DateUtil;
import io.swagger.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final String PENDING_STATUS = "pending";
    private final String ACTIVE_STATUS = "active";

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserClientScopeService userClientScopeService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientService clientService;

    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.contextPath}")
    private String serverContext;

    @Value("${server.activates.path}")
    private String activatePath;

    @Value("${activateKey.timeout}")
    private String activateKeyTimeout;


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
    public User signup(User user, String activateKey, String clientId) throws Exception {

        // 펀딩 유저 활성화 안되어 있으면 Exception
        PendingUserResponse registerPendingUser = userDao.findByLastPending(user.getEmail());


        if(PENDING_STATUS.equals(registerPendingUser.getStatus())
                && registerPendingUser.getActivateKey().equals(activateKey)){
            registerPendingUser.setStatus(ACTIVE_STATUS);
        }

        if(registerPendingUser == null
                || !ACTIVE_STATUS.equals(registerPendingUser.getStatus())
                || !DateUtil.isExpireDate(registerPendingUser.getExpireDate())){
            throw new ApiException("invalid pending");
        }

        Integer isUserCount = isUserId(user.getUserId());
        if (isUserCount != 0) {
            throw new ApiException("invalid userId");
        }
        String password = user.getPassword();
        if (password == null || "".equals(password)) {
            throw new ApiException("invalid password");
        }

        // 회원 정보 등록
        userDao.insertUser(user);

        // 펀딩 정보 제거
        deletePendingUser(user.getEmail());

        // 처음 회원가입한 클라이언트 맵핑 테이블 등록
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setClientId(clientId);
        userClientScope.setUserId(user.getUserId());
        userClientScopeService.insertUserClientScope(userClientScope);

        // 등록된 DB 유저 가져오기
        User registerUser = findByUser(user.getUserId());
        return registerUser;
    }

    /*
     * 유저 일괄 삭제
     * */
    @Override
    public void deleteUser(String userId, String client) {

        // 토큰 정보 삭제
        tokenService.deleteSearchUserId(userId);

        // 유저,클라이언트 관계 데이터 삭제
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setUserId(userId);
        userClientScope.setClientId(client);

        userClientScopeService.deleteUserClientScope(userClientScope);

        // 유저와 클라이언트 마지막 확인 후 마지막 이면 유저 정보 삭제 조회
        Integer userClientCount = userClientScopeService.findUserCount(userId);
        // 유저와 클라이언트 관계가 마지막일 경우 회원 정보 삭제
        if(userClientCount == 0){
            userDao.deleteUser(userId);
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
    public List<User> findByUsers(Map<String, String> search) {
        return userDao.findByUsers(search);
    }

    /*
     * 회원 가입 대기 유저 정보 등록
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public PendingUserResponse insertPendingUser(PendingUserRequest pendingUserRequest) throws Exception {
        PendingUserResponse pendingUserResponse = new PendingUserResponse();

        String email = pendingUserRequest.getEmail();
        String clientId = pendingUserRequest.getClientId();

        if(email == null || "".equals(email) || !email.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$")){
            throw new ApiException("invalid email");
        }else if(clientId == null || "".equals(clientId)){
            throw new ApiException("invalid clientId");
        }
        if(userDao.isEmail(email)){
            throw new ApiException("invalid email");
        }
        if(clientService.findByClient(clientId) == null){
            throw new ApiException("not found client");
        }

        userDao.deletePendingUser(email);
        String mTime = String.valueOf(System.currentTimeMillis());
        String activateKey = RandomUtil.randomString(32 - mTime.length()) + mTime;

        String ctx = serverContext == null ? "" : serverContext;
        //String retryUrl = serverHost + ":" + serverPort + ctx + activatePath +"?activateKey=" + activateKey;

        pendingUserResponse.setEmail(email);
        pendingUserResponse.setActivateKey(activateKey);
        //pendingUserResponse.setRetryUrl(retryUrl);
        pendingUserResponse.setStatus(PENDING_STATUS);
        pendingUserResponse.setClientId(clientId);

        // 요청시간과 만료시간 생성
        Date requestDate = DateUtil.requestDate();
        int min = activateKeyTimeout == null ? 60 : Integer.parseInt(activateKeyTimeout);
        Date expireDate = DateUtil.appendDate(requestDate, min);
        pendingUserResponse.setExpireDate(DateUtil.dateFormat(expireDate));

        userDao.insertPendingUser(pendingUserResponse);
        return findByPendingUserInfo(activateKey);
    }

    /*
     * 회원 가입 대기 유저 조회
     */
    @Override
    public PendingUserResponse findByPendingUserInfo(String activateKey) {
        PendingUserResponse pendingUserResponse = new PendingUserResponse();
        pendingUserResponse.setActivateKey(activateKey);
        return userDao.findByPendingUser(pendingUserResponse);
    }

    /*
     * 회원 가입 대기 상태 변경
     */
    public PendingUserResponse updatePendingStatus(String activateKey){
        PendingUserResponse pendingUserResponse = new PendingUserResponse();
        pendingUserResponse.setActivateKey(activateKey);
        pendingUserResponse.setStatus(ACTIVE_STATUS);
        userDao.updatePendingStatus(pendingUserResponse);
        return findByPendingUserInfo(activateKey);
    }

    public int selectUserCount(Map<String, String> search){
        return userDao.selectUserCount(search);
    }

    /*
     * 대기 유저 전체 조회
     */
    public List<PendingUserResponse> findByPendingUserInfoList(){
        return userDao.findByPendingUserInfoList();
    }

    /*
     * 대기 유저 전체 삭제
     */
    @Override
    public void deleteAllPendUser() {
        userDao.deleteAllPendUser();
    }

    /*
     * 대기 유저 삭제
     */
    @Override
    public void deletePendingUser(String email) {
        userDao.deletePendingUser(email);
    }
}