package io.swagger.api;

import io.swagger.model.Client;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Api(value = "clients", description = "the clients API")
public interface ClientsApi {

    @ApiOperation(value = "delete client", notes = "delete client", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> clientsClientIdDelete(@ApiParam(value = "target client id", required = true) @PathVariable("clientId") String clientId,
                                               @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication);


    @ApiOperation(value = "client info", notes = "client info", response = Client.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.GET)
    ResponseEntity<?> clientsClientIdGet(@ApiParam(value = "", required = true) @PathVariable("clientId") String clientId,
                                              @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication);


    @ApiOperation(value = "update client", notes = "update client", response = Client.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.PUT)
    ResponseEntity<?> clientsClientIdPut(@ApiParam(value = "", required = true) @PathVariable("clientId") String clientId,
                                              @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication,
                                              @ApiParam(value = "") @RequestBody Client client);


    @ApiOperation(value = "client list (admin Only)", notes = "client list (admin Only)", response = Client.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients",
        method = RequestMethod.GET)
    ResponseEntity<?> clientsGet(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication);


    @ApiOperation(value = "create client (admin Only)", notes = "create client (admin Only)", response = Client.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients",
        method = RequestMethod.POST)
    ResponseEntity<?> clientsPost(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authentication", required = true) String authentication,
                                       @ApiParam(value = "", required = true) @RequestBody Client client);

    @ApiOperation(value = "create UserClientScope", notes = "createUserClientScope", response = Client.class, tags={  })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/userClientScope/{userId}",
            method = RequestMethod.POST)
    ResponseEntity<?> userClientScopePost(@ApiParam(value = "target", required = true) @PathVariable("userId") String userId,
                                          @RequestParam(value = "clientId", required = true) String clientId);
}
