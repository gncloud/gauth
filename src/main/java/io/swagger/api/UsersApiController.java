package io.swagger.api;

import io.swagger.model.User;

import io.swagger.annotations.*;

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


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T05:57:02.655Z")

@Controller
public class UsersApiController implements UsersApi {

    public ResponseEntity<Void> usersGet(@ApiParam(value = "search keyword") @RequestParam(value = "search", required = false) String search) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> usersPost(@ApiParam(value = "" ,required=true ) @RequestBody User user) {
        // do some magic!
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public ResponseEntity<Void> usersUserIdDelete(@ApiParam(value = "user id",required=true ) @PathVariable("userId") String userId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<User> usersUserIdGet(@ApiParam(value = "search userId",required=true ) @PathVariable("userId") String userId) {
        // do some magic!
        return new ResponseEntity<User>(HttpStatus.OK);
    }

    public ResponseEntity<User> usersUserIdPut(@ApiParam(value = ""  ) @RequestBody User user) {
        // do some magic!
        return new ResponseEntity<User>(HttpStatus.OK);
    }

}
