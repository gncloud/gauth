package io.swagger.api;

import io.swagger.model.Token;
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
public class TokenApiController implements TokenApi {

    public ResponseEntity<Void> tokenDelete(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Token> tokenGet(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        // do some magic!
        return new ResponseEntity<Token>(HttpStatus.OK);
    }

    public ResponseEntity<Token> tokenPost(@ApiParam(value = "" ,required=true ) @RequestBody User user) {
        // do some magic!
        return new ResponseEntity<Token>(HttpStatus.OK);
    }

}
