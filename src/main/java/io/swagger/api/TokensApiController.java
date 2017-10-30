package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import io.swagger.service.ClientService;
import io.swagger.service.TokenService;
import io.swagger.service.UserClientScopeService;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.security.AccessControlException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class TokensApiController implements TokensApi {

    private static Logger logger = LoggerFactory.getLogger(TokensApiController.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserClientScopeService userClientScopeService;

    @Autowired
    private ClientService clientService;

    public ResponseEntity<?> tokenDelete(@RequestHeader(value="Authorization", required=true) String authorization) {
        try {

            tokenService.deleteToken(authorization);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokensDelete", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensGet(@ApiParam(value = "token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            Token registerToken = tokenService.getToken(authorization);
            if(registerToken == null){
                throw new NotFoundException(404, "token empty");
            }
            return new ResponseEntity<Token>(registerToken, HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (NotFoundException e){
            logger.warn("tokensGet {}", e.getMessage());
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            logger.error("tokensGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensPost(@ApiParam(value = "" ,required=true ) @RequestBody AuthenticationRequest user) {
        try {

            User targetUser = userService.getUser(user);
            if(targetUser == null){
                throw new NotFoundException(404, "invalid userId");
            }

            List<UserClientScope> registerRelation = null;
            boolean isCreateToken = false;

            UserClientScope userClientScope = new UserClientScope();
            if(clientService.ADMIN_CLIENT_ID.equals(user.getClientId())){
                registerRelation = userClientScopeService.findUserCodeByRelation(targetUser.getUserCode());
                for(int i=0; i < registerRelation.size(); i++){
                    if(clientService.SCOPE_ADMIN.equals(registerRelation.get(i).getScopeId())){
                        isCreateToken = true;
                        break;
                    }
                }
            }else{
                userClientScope.setUserCode(targetUser.getUserCode());
                userClientScope.setClientId(user.getClientId());
                registerRelation = userClientScopeService.findRelation(userClientScope);
                if(registerRelation != null && registerRelation.size() > 0){
                    isCreateToken = true;
                }
            }

            Token registerToken = null;
            if(isCreateToken){
                registerToken = tokenService.createToken(user);
            }else{
                throw new AccessControlException("userClientScope empty");
            }
            return new ResponseEntity<Token>(registerToken, HttpStatus.OK);
        } catch(AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.INFO, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof NotFoundException){
                httpStatus = HttpStatus.NOT_FOUND;
                logger.warn("NotFoundException {}", e.getMessage());
            }else if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("tokensPost error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
        }
    }

    public ResponseEntity<?> tokensTokenIdDelete(@ApiParam(value = "target token",required=true ) @PathVariable("tokenId") String tokenId,
                                                 @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);
            tokenService.deleteToken(tokenId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("tokensTokenIdDelete error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
        }
    }

    public ResponseEntity<?> tokensTokenIdGet(@ApiParam(value = "read token info",required=true ) @PathVariable("tokenId") String tokenId) {
        try {
            Token registerToken = tokenService.getToken(tokenId);
            return new ResponseEntity<Token>(registerToken, HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokensTokenIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> tokensPut(@ApiParam(value = "token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            Token registerToken = tokenService.isTokenValid(authorization);
            registerToken = tokenService.refreshTokenExpireDate(registerToken.getTokenId());

            return new ResponseEntity<Token>(registerToken, HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            logger.error("tokensTokenIdDelete error", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
        }
    }

}
