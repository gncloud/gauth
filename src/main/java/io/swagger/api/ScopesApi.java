package io.swagger.api;

import io.swagger.annotations.*;
import io.swagger.model.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "scopes", description = "the scopes API")
public interface ScopesApi {

    @ApiOperation(value = "scope info list (admin Only)", notes = "scope info list (admin Only)", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/scopes",
        method = RequestMethod.GET)
    ResponseEntity<?> scopesGet(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication,
                                   @ApiParam(value = "") @RequestParam String client);


    @ApiOperation(value = "create scope (admin Only)", notes = "create scope (admin Only)", response = Scope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/scopes",
        method = RequestMethod.POST)
    ResponseEntity<?> scopesPost(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication,
                                     @ApiParam(value = "") @RequestBody Scope scope);


    @ApiOperation(value = "delete scope", notes = "delete scope", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/scopes/{scopeId}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> scopesScopeIdDelete(@ApiParam(value = "", required = true) @PathVariable("scopeId") String scopeId,
                                          @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication,
                                          @ApiParam(value = "clientId", required = true) @RequestParam(value = "clientId", required = true) String clientId);

    @ApiOperation(value = "scope info", notes = "scope info", response = Scope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/scopes/{scopeId}",
        method = RequestMethod.GET)
    ResponseEntity<?> scopesScopeIdGet(@ApiParam(value = "", required = true) @PathVariable("scopeId") String scopeId,
                                       @ApiParam(value = "clientId", required = true) @RequestParam(value = "clientId", required = true) String clientId);

    @ApiOperation(value = "update scope", notes = "update scope", response = Scope.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Scope.class) })
    @RequestMapping(value = "/scopes/{scopeId}",
        method = RequestMethod.PUT)
    ResponseEntity<?> scopesScopeIdPut(@ApiParam(value = "", required = true) @PathVariable("scopeId") String scopeId,
                                           @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication,
                                           @ApiParam(value = "") @RequestBody Scope scope);

}
