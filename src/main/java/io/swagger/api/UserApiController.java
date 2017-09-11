package io.swagger.api;

import io.swagger.model.User;

import io.swagger.annotations.*;

import io.swagger.service.UserService;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Controller
public class UserApiController implements UserApi {

    private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;

    public ResponseEntity<Void> userDelete(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
        @ApiParam(value = "client id", required = true) @RequestParam(value = "clientId", required = true) String clientId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> userGet(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {

        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public ResponseEntity<User> userPut(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
        @ApiParam(value = "" ,required=true ) @RequestBody User user) {
        try{
            user.setTokenId(authorization);
            User registerUser = userService.updateUser(user);
            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
        } catch (Exception e){
            logger.debug("userPut : {}", e.getMessage());
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }
    }

}
