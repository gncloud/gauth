package io.swagger.api;

import io.swagger.model.User;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Api(value = "users", description = "the users API")
public interface UsersApi {

    @ApiOperation(value = "search all  user", notes = "search all  user", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.GET)
    ResponseEntity<Void> usersGet(@ApiParam(value = "search keyword") @RequestParam(value = "search", required = false) String search);


    @ApiOperation(value = "user sign up", notes = "user sign up", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.POST)
    ResponseEntity<User> usersPost(@ApiParam(value = "" ,required=true ) @RequestBody User user) throws ApiException;


    @ApiOperation(value = "integrated delete user", notes = "integrated delete user", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/users/{userId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> usersUserIdDelete(@ApiParam(value = "user id",required=true ) @PathVariable("userId") String userId);


    @ApiOperation(value = "read users", notes = "read users", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/users/{userId}",
        method = RequestMethod.GET)
    ResponseEntity<User> usersUserIdGet(@ApiParam(value = "search userId",required=true ) @PathVariable("userId") String userId);


    @ApiOperation(value = "update user", notes = "update user", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/users/{userId}",
        method = RequestMethod.PUT)
    ResponseEntity<User> usersUserIdPut(@ApiParam(value = ""  ) @RequestBody User user);

}
