package io.swagger.api;


import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "validateToken", description = "the validateToken API")
public interface ValidateTokenApi {

    @ApiOperation(value = "token validate check", notes = "token validate check", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/validateToken",
        method = RequestMethod.HEAD)
    ResponseEntity<?> validateTokenHead(@ApiParam(value = "User Authorization BEARER Token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);

}
