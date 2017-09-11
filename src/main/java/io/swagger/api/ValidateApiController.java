package io.swagger.api;


import io.swagger.annotations.*;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.logging.Logger;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@RestController
public class ValidateApiController implements ValidateApi {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(ValidateApiController.class);

    public ResponseEntity<Void> validateHead(@ApiParam(value = "User Authorization BEARER Token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
