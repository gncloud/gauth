package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.User;
import io.swagger.service.TokenService;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class UserApiController implements UserApi {

    private Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> userGet(@ApiParam(value = "User Authentication BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {

            tokenService.isTokenValid(authorization);

            User registerUser = userService.fienTokenByUser(authorization);

            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
        } catch (Exception e){
            logger.error("userGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

}
