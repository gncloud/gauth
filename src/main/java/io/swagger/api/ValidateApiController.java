package io.swagger.api;


import io.swagger.annotations.ApiParam;
import io.swagger.service.TokenService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@RestController
public class ValidateApiController implements ValidateApi {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ValidateApiController.class);

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<Void> validateHead(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization, @RequestParam String client) {

        try {

            boolean isValid = tokenService.isTokenValidate(authorization, client);

            if(isValid){
                return new ResponseEntity<Void>(HttpStatus.OK);
            }else{
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            logger.error("validateHead", e);
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
}
