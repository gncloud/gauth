package io.swagger.api;

import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;

import io.swagger.annotations.*;

import io.swagger.model.User;
import io.swagger.service.TokenService;
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

    public ResponseEntity<?> tokenDelete(@RequestHeader(value="Authentication", required=true) String authentication) {
        try {
            tokenService.deleteToekn(authentication);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokensDelete", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensGet(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authentication", required=true) String authentication) {
        try {
            tokenService.isAdminToken(authentication);
            List<Token> registerTokenList = tokenService.selectToken();
            return new ResponseEntity<List<Token>>(registerTokenList, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("tokensGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensPost(@ApiParam(value = "" ,required=true ) @RequestBody AuthenticationRequest user) {
        try {

            User targetUser = userService.findByUser(user.getUserId());
            if(targetUser == null){
                throw new Exception("user invalid");
            }else if(!targetUser.isEqualsPassword(user.getPassword())){
                throw new Exception("password invalid");
            }

            Token registerToken = tokenService.createToken(user);

            return new ResponseEntity<Token>(registerToken, HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokensPost", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensTokenIdDelete(@ApiParam(value = "target token",required=true ) @PathVariable("tokenId") String tokenId,
                                                 @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authentication", required=true) String authentication) {
        try {
            tokenService.isAdminToken(authentication);
            tokenService.deleteToekn(tokenId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("tokensTokenIdDelete", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensTokenIdGet(@ApiParam(value = "read token info",required=true ) @PathVariable("tokenId") String tokenId) {
        try {
            Token registerToken = tokenService.findByToken(tokenId);
            return new ResponseEntity<Token>(registerToken, HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokensTokenIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
