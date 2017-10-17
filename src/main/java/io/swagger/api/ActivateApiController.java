package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.PendingUserRequest;
import io.swagger.model.PendingUserResponse;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class ActivateApiController implements ActivateApi {

    private static Logger logger = LoggerFactory.getLogger(ActivateApiController.class);

    @Autowired
    private UserService userService;

    public ResponseEntity<?> registerPost(@ApiParam(value = ""  ) @RequestBody PendingUserRequest pendingUserRequest) {
        try {
            PendingUserResponse pendingUserResponse = userService.insertPendingUser(pendingUserRequest);
            return new ResponseEntity<PendingUserResponse>(pendingUserResponse, HttpStatus.OK);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("activatePost error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    @Override
    public ResponseEntity<?> activatesPut(@ApiParam(value = "") @RequestParam(value = "activateKey" ,required=true ) String activateKey) {
        try {

            PendingUserResponse registerPendingUser = userService.updatePendUserActive(activateKey);

            return new ResponseEntity<PendingUserResponse>(registerPendingUser, HttpStatus.OK);
        }  catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("activatesPut error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

}
