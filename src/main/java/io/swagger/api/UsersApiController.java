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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.AccessControlException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class UsersApiController implements UsersApi {

    private static Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> usersGet(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                      @ApiParam(value = "search keyword") @RequestParam(value = "search", required = false) String search) {
        try {
            tokenService.isAdminToken(authorization);

            List<User> registerUserList = userService.findByUsers(search);

            return new ResponseEntity<List<User>>(registerUserList, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("usersGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> usersPost(@ApiParam(value = "client id", required = true) @RequestParam(value = "clientId", required = true) String clientId,
                                       @ApiParam(value = "" ,required=true ) @RequestBody User user) {
        try {

            User registerUser = userService.signup(user);

            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("usersGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> usersUserIdDelete(@ApiParam(value = "delete target",required=true ) @PathVariable("userId") String userId,
                                               @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);

            userService.deleteUser(userId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("usersGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> usersUserIdGet(@ApiParam(value = "search userId",required=true ) @PathVariable("userId") String userId,
                                            @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);
            User registerUser = userService.findByUser(userId);

            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("usersUserIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> usersUseridPut(@ApiParam(value = "search userId",required=true ) @PathVariable("userId") String userId,
                                            @ApiParam(value = "user token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                            @RequestBody User user) {
        try {

            user.setUserId(userId);
            User registerUser = userService.updateUser(user);
            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
        } catch (Exception e){
            logger.error("usersUserIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
