package io.swagger.api;

import io.swagger.model.Client;
import io.swagger.model.Scope;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Api(value = "scopes", description = "the scopes API")
public interface ScopesApi {

    @ApiOperation(value = "read scope", notes = "read scope", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/scopes",
        method = RequestMethod.GET)
    ResponseEntity<Scope> scopesGet(@ApiParam(value = ""  ) @RequestBody Client client);


    @ApiOperation(value = "create scope", notes = "create scope", response = Scope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/scopes",
        method = RequestMethod.POST)
    ResponseEntity<Scope> scopesPost(@ApiParam(value = ""  ) @RequestBody Scope scope);


    @ApiOperation(value = "delete scope", notes = "delete scope", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/scopes/{scopeId}",
        method = RequestMethod.DELETE)
    ResponseEntity<Void> scopesScopeIdDelete(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId);


    @ApiOperation(value = "read scope", notes = "read scope", response = Scope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/scopes/{scopeId}",
        method = RequestMethod.GET)
    ResponseEntity<Scope> scopesScopeIdGet(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId);


    @ApiOperation(value = "update scope", notes = "update scope", response = Scope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/scopes/{scopeId}",
        method = RequestMethod.PUT)
    ResponseEntity<Scope> scopesScopeIdPut(@ApiParam(value = "",required=true ) @PathVariable("scopeId") String scopeId,
        @ApiParam(value = ""  ) @RequestBody Scope scope);

}
