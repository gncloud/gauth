package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.AuthenticationRequest;
import io.swagger.model.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "tokens", description = "the tokens API")
public interface TokensApi {

    @ApiOperation(value = "remove Token", notes = "remove Token", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/tokens/{tokenId}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> tokensDelete(@ApiParam(value = "read token info", required = true) @PathVariable("tokenId") String tokenId);


    @ApiOperation(value = "Token All List (admin Only)", notes = "Token All List (admin Only)", response = Token.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Token.class) })
    @RequestMapping(value = "/tokens",
        method = RequestMethod.GET)
    ResponseEntity<?> tokensGet(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "create token / login action", notes = "create token / login action", response = Token.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Token.class) })
    @RequestMapping(value = "/tokens",
        method = RequestMethod.POST)
    ResponseEntity<?> tokensPost(@ApiParam(value = "", required = true) @RequestBody AuthenticationRequest user);


    @ApiOperation(value = "delete token (admin Only)", notes = "delete token (admin Only)", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/tokens/{tokenId}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> tokensTokenIdDelete(@ApiParam(value = "target token", required = true) @PathVariable("tokenId") String tokenId,
                                             @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization);


    @ApiOperation(value = "read token info", notes = "read token info", response = Token.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Token.class) })
    @RequestMapping(value = "/tokens/{tokenId}",
        method = RequestMethod.GET)
    ResponseEntity<?> tokensTokenIdGet(@ApiParam(value = "read token info", required = true) @PathVariable("tokenId") String tokenId);

}
