package io.swagger.api;

import io.swagger.model.Client;
import io.swagger.model.Scope;

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
public class ScopesApiController implements ScopesApi {

    public ResponseEntity<Void> scopesGet(@ApiParam(value = ""  ) @RequestBody Client client) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Scope> scopesPost(@ApiParam(value = ""  ) @RequestBody Scope scope) {
        // do some magic!
        return new ResponseEntity<Scope>(HttpStatus.OK);
    }

    public ResponseEntity<Void> scopesScopeIdDelete(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Scope> scopesScopeIdGet(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId) {
        // do some magic!
        return new ResponseEntity<Scope>(HttpStatus.OK);
    }

    public ResponseEntity<Scope> scopesScopeIdPut(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId,
        @ApiParam(value = ""  ) @RequestBody Scope scope) {
        // do some magic!
        return new ResponseEntity<Scope>(HttpStatus.OK);
    }

}
