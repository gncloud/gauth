package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Token;
import io.swagger.service.TokenService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Controller
public class TokensApiController implements TokensApi {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TokenApiController.class);

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> tokensGet() {
        try {

            List<Token> registerTokenList = tokenService.selectToken();
            return new ResponseEntity<>(registerTokenList, OK);
        } catch (Exception e){
            logger.error("tokensGet", e);
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    public ResponseEntity<?> tokensTokenIdDelete(@ApiParam(value = "delete token", required = true) @PathVariable("tokenId") String tokenId) {
        try {
            tokenService.deleteToekn(tokenId);
            return new ResponseEntity<>(OK);
        } catch (Exception e){
            logger.error("tokensTokenIdDelete", e);
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

    public ResponseEntity<Token> tokensTokenIdGet(@ApiParam(value = "read token info",required=true ) @PathVariable("tokenId") String tokenId) {
        try {
            Token registerToken = tokenService.findByToken(tokenId);
            return new ResponseEntity<>(registerToken,OK);
        } catch (Exception e){
            logger.error("tokensTokenIdGet", e);
            return new ResponseEntity<>(BAD_REQUEST);
        }
    }

}
