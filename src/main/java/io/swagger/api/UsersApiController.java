package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.User;
import io.swagger.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@RestController
public class UsersApiController implements UsersApi {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;


    public ResponseEntity<List<User>> usersGet(@ApiParam(value = "search keyword") @RequestParam(value = "search", required = false) String search) {
        List<User> registerUsers = userService.findByUsers(search);
        return new ResponseEntity<List<User>>(registerUsers, OK);
    }

    public ResponseEntity<User> usersPost(@ApiParam(value = "", required = true) @RequestBody User user) throws ApiException {
        try {
            User registerUser = userService.signup(user);
            return new ResponseEntity<User>(registerUser, OK);
        } catch (Exception e){
            logger.error("usersPost", e);
            return new ResponseEntity<User>(BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> usersUserIdDelete(@ApiParam(value = "user id", required = true) @PathVariable("userId") String userId) {
        try {
            User user = new User();
            user.setUserId(userId);
            userService.deleteUser(user);
            return new ResponseEntity<>(OK);
        } catch (Exception e){
            logger.error("usersUserIdDelete", e);
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    public ResponseEntity<User> usersUserIdGet(@ApiParam(value = "search userId", required = true) @PathVariable("userId") String userId) {
        try {
            User registerUser = userService.findByUser(userId);
            return new ResponseEntity<User>(registerUser, OK);
        } catch (Exception e){
            logger.error("usersUserIdGet", e);
            return new ResponseEntity<User>(BAD_REQUEST);
        }
    }

    public ResponseEntity<User> usersUserIdPut(@PathVariable("userId") String userId, @ApiParam(value = "") @RequestBody User user) {
        try {
            user.setUserId(userId);
            User registerUser = userService.updateUser(user);
            return new ResponseEntity<User>(registerUser, OK);
        } catch (Exception e){
            logger.error("usersUserIdPut", e);
            return new ResponseEntity<User>(BAD_REQUEST);
        }
    }

}
