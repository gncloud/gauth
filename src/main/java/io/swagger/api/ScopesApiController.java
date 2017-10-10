package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Client;
import io.swagger.model.Scope;
import io.swagger.service.ScopeService;
import io.swagger.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.AccessControlException;
import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class ScopesApiController implements ScopesApi {

    private static Logger logger = LoggerFactory.getLogger(ScopesApiController.class);

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> scopesGet(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                       @ApiParam(value = ""  ) @RequestParam String client) {
        try {
            tokenService.isAdminToken(authorization);
            Client searchClient = new Client();
            searchClient.setClientId(client);
            List<Scope> clientScopeList = scopeService.selectClientScope(searchClient);

            return new ResponseEntity<List<Scope>>(clientScopeList, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("scopesGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> scopesPost(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                        @ApiParam(value = ""  ) @RequestBody Scope scope) {
        try {

            tokenService.isAdminToken(authorization);

            Scope registerScope = scopeService.insertScope(scope);

            return new ResponseEntity<Scope>(registerScope, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("scopesPost", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> scopesScopeIdDelete(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId,
                                                 @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                                 @RequestParam(value = "clientId", required = true) String clientId) {
        try {

            tokenService.isAdminToken(authorization);

            Scope scope = new Scope();
            scope.setScopeId(scopeId);
            scope.setClientId(clientId);

            scopeService.deleteScope(scope);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("scopesScopeIdDelete", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> scopesScopeIdGet(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId,
                                              @RequestParam(value = "clientId", required = true) String clientId) {
        try {

            Scope scope = new Scope();
            scope.setScopeId(scopeId);
            scope.setClientId(clientId);

            Scope registerScope = scopeService.selectScope(scope);

            return new ResponseEntity<Scope>(registerScope, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("scopesScopeIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> scopesScopeIdPut(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId,
                                              @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                              @ApiParam(value = ""  ) @RequestBody Scope scope) {
        try {

            tokenService.isAdminToken(authorization);

            scope.setScopeId(scopeId);

            Scope registerScope = scopeService.updateScope(scope);

            return new ResponseEntity<>(registerScope, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("scopesScopeIdPut", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
