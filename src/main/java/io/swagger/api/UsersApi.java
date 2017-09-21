package io.swagger.api;

import io.swagger.model.Scope;
import io.swagger.model.User;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "users", description = "the users API")
public interface UsersApi {

    @ApiOperation(value = "user info keyword search (admin Only)", notes = "user info keyword search (admin Only)", response = User.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.GET)
    ResponseEntity<?> usersGet(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                        @ApiParam(value = "search keyword") @RequestParam(value = "search", required = false) String search);


    @ApiOperation(value = "user sign up", notes = "user sign up", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/users",
        method = RequestMethod.POST)
    ResponseEntity<?> usersPost(@ApiParam(value = "client id", required = true) @RequestParam(value = "clientId", required = true) String clientId,
                                   @ApiParam(value = "", required = true) @RequestBody User user,
                                @ApiParam(value = "activateKey") @RequestParam(value = "activateKey", required = false) String activateKey);


    @ApiOperation(value = "delete user", notes = "delete user", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/users/{userId}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> usersUserIdDelete(@ApiParam(value = "delete target", required = true) @PathVariable("userId") String userId,
                                           @ApiParam(value = "User Authorization BEARER Token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                        @ApiParam(value = "client id" ,required=true ) @RequestParam String client);


    @ApiOperation(value = "user info (admin only)", notes = "user info (admin only)", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/users/{userId}",
        method = RequestMethod.GET)
    ResponseEntity<?> usersUserIdGet(@ApiParam(value = "search userId", required = true) @PathVariable("userId") String userId,
                                        @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "update user", notes = "update user", response = User.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/users/{userId}",
            method = RequestMethod.PUT)
    ResponseEntity<?> usersUseridPut(@ApiParam(value = "update user", required = true) @PathVariable("userId") String userId,
                                       @ApiParam(value = "user token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                       @ApiParam(value = "") @RequestBody User user);
}
