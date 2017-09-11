package io.swagger.api;

import io.swagger.model.Client;
import io.swagger.model.Scope;

import io.swagger.annotations.*;

import io.swagger.service.ScopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Controller
public class ScopesApiController implements ScopesApi {
    private static Logger logger = LoggerFactory.getLogger(ScopesApiController.class);

    @Autowired
    private ScopeService scopeService;


    public ResponseEntity<List<Scope>> scopesGet(@ApiParam(value = "") @RequestBody Client client) {
        try {
            Scope scope = new Scope();
            scope.setClientId(client.getClientId());
            List<Scope> registerScope = scopeService.selectClientScope(client);

            return new ResponseEntity<>(registerScope, HttpStatus.OK);
        } catch (Exception e){
            logger.error("scopesGet ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Scope> scopesPost(@ApiParam(value = ""  ) @RequestBody Scope scope) {
        try {
            Scope registerScope = scopeService.insertScope(scope);
            return new ResponseEntity<>(registerScope, HttpStatus.OK);
        } catch (Exception e){
            logger.error("scopesPost ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> scopesScopeIdDelete(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId
                                                    , @ApiParam(value = ""  )  @RequestBody Scope scope) {
        try {

            scope.setScopeId(scopeId);
            scopeService.deleteScope(scope);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logger.error("scopesPost ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
