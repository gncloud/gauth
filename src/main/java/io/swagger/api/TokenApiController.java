package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.service.TokenService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Controller
public class TokenApiController implements TokenApi {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TokenApiController.class);

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<Void> tokenDelete(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.deleteToekn(authorization);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokenDelete ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Token> tokenGet(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            Token registerToken = tokenService.findByToken(authorization);
            return new ResponseEntity<>(registerToken, HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokenGet ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Token> tokenPost(@ApiParam(value = "" ,required=true ) @RequestBody User user) {
        try {
            Token registerToken = tokenService.createToken(user);
            return new ResponseEntity<>(registerToken, HttpStatus.OK);
        } catch (Exception e){
            logger.error("tokenPost ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
