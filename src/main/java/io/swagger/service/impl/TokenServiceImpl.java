package io.swagger.service.impl;

import io.swagger.api.ApiException;
import io.swagger.dao.TokenDao;
import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import io.swagger.model.User;
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

import java.security.AccessControlException;
import java.util.Date;
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
    public Token createToken(AuthenticationRequest user) throws Exception {

        // 유저가 클라이언트에 등록 여부를 확인한다.
        if(!userClientScopeService.isUserClientScope(user)){
            logger.debug("user userClientScope empty");
            throw new AccessControlException("user userClientScope empty");
        }

        // 기존 토큰 삭제
        deleteSearchUserId(user.getUserId());


        // 요청시간과 만료시간 생성
        Date requestDate = DateUtil.requestDate();
        int min = tokenTimeout == null ? 60 : Integer.parseInt(tokenTimeout);
        Date expireDate = DateUtil.appendDate(requestDate, min);

        // 토큰 생성
        String userToken = generateToken(user.getUserId(), DateUtil.dateFormat(expireDate));

        // DB 저장
        Token token = new Token();
        token.setUserId(user.getUserId());
        token.setClientId(user.getClientId());
        token.setTokenId(userToken);
        token.setCreateTime(DateUtil.dateFormat(requestDate));
        token.setExpireDate(DateUtil.dateFormat(expireDate));

        tokenDao.insertToekn(token);

        Token registerToken = findByToken(token.getTokenId());

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 조회
     */
    public Token findByToken(String tokenId) throws Exception {

        Token registerToken = tokenDao.findByToken(tokenId);
        if(registerToken == null){
            throw new ApiException("invalid Token");
        }

        return registerToken;
    }

    /*
     * 토큰 아이디로 토큰 정보 삭제
     */
    public void deleteToekn(String tokenId){
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
    public Token isTokenValidate(String token, String client) throws Exception, AccessControlException{

        Token registerToken = isTokenValidate(token);

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
    public Token isTokenValidate(String token) throws Exception {

        // 토큰으로 유저 정보 조회, 없으면 Exception
        isUser(token);

        // DB 저장된 토큰 추가 정보 조회
        Token registerToken = tokenDao.findByToken(token);

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
        User registerUser = userService.fienByTokenToUserInfo(token);
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
    public Token findByUserToToken(AuthenticationRequest authenticationRequest) throws Exception {
        return tokenDao.findByToken(authenticationRequest);
    }

    /*
     * admin check
     */
    @Override
    public void isAdminToken(String authentication) throws AccessControlException {
        Token token = new Token();
        token.setTokenId(authentication);
        token.setClientId(TokenService.ADMIN_CLIENT);
        Token registerAdminToken = tokenDao.findByAdminToken(token);

        if(registerAdminToken == null || !DateUtil.isExpireDate(registerAdminToken.getExpireDate())){
            throw new AccessControlException("token invalid");
        }
    }

    @Override
    public void deleteSearchUserId(String userId){
        AuthenticationRequest user = new AuthenticationRequest();
        user.setUserId(userId);
        tokenDao.deleteClientToken(user);
    }

    /*
     * delete client
     */
    @Override
    public void deleteClient(String clientId){
        tokenDao.deleteClient(clientId);
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
