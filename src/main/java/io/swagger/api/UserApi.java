package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "user", description = "the user API")
public interface UserApi {

    @ApiOperation(value = "my info", notes = "my info", response = User.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = User.class) })
    @RequestMapping(value = "/user",
        method = RequestMethod.GET)
    ResponseEntity<?> userGet(@ApiParam(value = "User Authentication BEARER Token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication);



}
