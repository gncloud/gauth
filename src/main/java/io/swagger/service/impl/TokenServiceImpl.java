package io.swagger.service.impl;

import io.swagger.api.ApiException;
import io.swagger.api.NotFoundException;
import io.swagger.dao.TokenDao;
import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import io.swagger.service.ClientService;
import io.swagger.service.TokenService;
import io.swagger.service.UserClientScopeService;
import io.swagger.service.UserService;
import io.swagger.util.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.AccessControlException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TokenServiceImpl implements TokenService{

    private static Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserClientScopeService userClientScopeService;


    @Value("${user.token.timeout}")
    private String tokenTimeout;

    /*
     * 로그인 토큰 생성
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Token createToken(AuthenticationRequest user) throws Exception {


        User findUser = new User();
        findUser.setUserId(user.getUserId());
        User registerUser = userService.getUser(user.getUserId());

        Iterator<UserClientScope> findUserScopeList = userClientScopeService.selectUserMappingList(registerUser).iterator();
        boolean isAdmin = false;
        while (findUserScopeList.hasNext()){
            UserClientScope userClientScope = findUserScopeList.next();
            if(userClientScope != null && ClientService.SCOPE_ADMIN.equals(userClientScope.getScopeId())){
                isAdmin = true;
                break;
            }
        }

        // 유저가 클라이언트에 등록 여부를 확인한다.
        user.setUserCode(registerUser.getUserCode());
        if(!userClientScopeService.isUserClientScope(user) && !isAdmin){
            logger.debug("user userClientScope empty");
            throw new AccessControlException("user userClientScope empty");
        }

        // 기존 토큰 삭제
        registerUser = userService.getUser(user.getUserId());
        if(registerUser != null){
            deleteUserCodeByToken(registerUser.getUserCode());
        }

        // 요청시간과 만료시간 생성
        Date requestDate = DateUtil.requestDate();
        int min = tokenTimeout == null ? 60 : Integer.parseInt(tokenTimeout);
        Date expireDate = DateUtil.appendDate(requestDate, min);

        // 토큰 생성
        String userToken = generateToken(registerUser.getUserId(), DateUtil.dateFormat(expireDate));

        // DB 저장
        Token token = new Token();
        token.setUserCode(registerUser.getUserCode());
        token.setClientId(user.getClientId());
        token.setTokenId(userToken);
        token.setCreateTime(DateUtil.dateFormat(requestDate));
        token.setExpireDate(DateUtil.dateFormat(expireDate));

        tokenDao.insertToken(token);

        Token registerToken = getToken(token.getTokenId());

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 조회
     */
    public Token getToken(String tokenId) throws Exception {

        Token registerToken = tokenDao.getToken(tokenId);
        if(registerToken == null){
            throw new ApiException("invalid Token");
        }

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 삭제
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteToken(String tokenId){
        tokenDao.deleteToken(tokenId);
    }

    /*
     * 토큰 전체 조회
     */
    @Override
    public List<Token> selectToken() {
        return tokenDao.selectTokens();
    }

    /*
     * 토큰 유효성 검사, 발급 클라이언트 도 확인
     */
    @Override
    public Token isTokenValid(String token, String client) throws Exception, AccessControlException{

        Token registerToken = isTokenValid(token);

        // 토큰 발급 클라이언트 확인
        String registerClientId = registerToken.getClientId();
        if(!registerClientId.equals(client)){
            throw new AccessControlException("invalid client");
        }

        return registerToken;
    }

    /*
     * 토큰 유효성 검사
     */
    @Override
    public Token isTokenValid(String token) throws Exception {

        // 토큰으로 유저 정보 조회, 없으면 Exception
        isUser(token);

        // DB 저장된 토큰 추가 정보 조회
        Token registerToken = tokenDao.getToken(token);

        if(registerToken == null){
            throw new NotFoundException(404, "invalid tokem");
        }

        boolean isExpireDate = DateUtil.isExpireDate(registerToken.getExpireDate());
        if(!isExpireDate){
            throw new AccessControlException("invalid expireDate");
        }

        return registerToken;
    }

    /*
     * 유저 확인
     */
    private void isUser(String token) throws Exception {
        // 토큰으로 유저 정보 조회
        User registerUser = userService.findTokenByUser(token);
        if(registerUser == null){
            logger.debug("Not Found User : {}", token);
            throw new ApiException("invalid token");
        }
    }

    /*
     * 유저로 토큰 정보 조회
     * 유저아이디, 클라이언트아이디 필수
     */
    @Override
    public Token findTokenByUser(AuthenticationRequest authenticationRequest) throws Exception {
        return tokenDao.findUserByToken(authenticationRequest);
    }

    /*
     * admin check
     */
    @Override
    public Token isAdminToken(String authentication) throws AccessControlException {

        Token token = new Token();
        token.setTokenId(authentication);
        Token registerAdminToken = tokenDao.findByAdminToken(token);

        if(registerAdminToken == null || !DateUtil.isExpireDate(registerAdminToken.getExpireDate())){
            throw new AccessControlException("token invalid");
        }
        return registerAdminToken;
    }

    /*
     * delete client
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteUserCodeByToken(int userCode){
        tokenDao.deleteTokenByUserCode(userCode);
    }

    /*
     * 클라이언트 연결된 토큰 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteClientIdByToken(String clientId){
        tokenDao.deleteClientIdByToken(clientId);
    }

    /*
     * 토큰 생성
     */
    private String generateToken(String uid, String date){
        // 유저 아이디와 만료시간으로 암호화된 값 생성
        String tokenSeed = uid + date;
        return DigestUtils.sha256Hex(tokenSeed);
    }

    private boolean isNull(String value){
        return (value == null || "".equals(value));
    }

}
