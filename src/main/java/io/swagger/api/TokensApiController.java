package io.swagger.api;

import io.swagger.model.Token;

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
public class TokensApiController implements TokensApi {

    public ResponseEntity<Void> tokensGet() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> tokensTokenIdDelete(@ApiParam(value = "delete token",required=true ) @PathVariable("tokenId") String tokenId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Token> tokensTokenIdGet(@ApiParam(value = "read token info",required=true ) @PathVariable("tokenId") String tokenId) {
        // do some magic!
        return new ResponseEntity<Token>(HttpStatus.OK);
    }

}
