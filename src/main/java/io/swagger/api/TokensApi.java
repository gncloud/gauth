package io.swagger.api;

import io.swagger.model.Token;

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

@Api(value = "tokens", description = "the tokens API")
public interface TokensApi {

    @ApiOperation(value = "all read token info", notes = "all read token info", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/tokens",
        method = RequestMethod.GET)
    ResponseEntity<List<Token>> tokensGet();


    @ApiOperation(value = "delete token", notes = "delete token", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/tokens/{tokenId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> tokensTokenIdDelete(@ApiParam(value = "delete token",required=true ) @PathVariable("tokenId") String tokenId);


    @ApiOperation(value = "read token info", notes = "read token info", response = Token.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Token.class) })
    @RequestMapping(value = "/tokens/{tokenId}",
        method = RequestMethod.GET)
    ResponseEntity<Token> tokensTokenIdGet(@ApiParam(value = "read token info",required=true ) @PathVariable("tokenId") String tokenId);

}
