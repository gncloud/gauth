package io.swagger.api;

import io.swagger.model.Client;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Api(value = "clients", description = "the clients API")
public interface ClientsApi {

    @ApiOperation(value = "delete client", notes = "delete client", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.DELETE)
    ResponseEntity<?> clientsClientIdDelete(@ApiParam(value = "", required = true) @PathVariable("clientId") String clientId);


    @ApiOperation(value = "read client", notes = "read client", response = Client.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.GET)
    ResponseEntity<Client> clientsClientIdGet(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId);


    @ApiOperation(value = "update client", notes = "update client", response = Client.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients/{clientId}",
        method = RequestMethod.PUT)
    ResponseEntity<Client> clientsClientIdPut(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId,
        @ApiParam(value = ""  ) @RequestBody Client client);


    @ApiOperation(value = "read clients", notes = "read clients", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Void.class) })
    @RequestMapping(value = "/clients",
        method = RequestMethod.GET)
    ResponseEntity<List<Client>> clientsGet();


    @ApiOperation(value = "create client", notes = "create client", response = Client.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Client.class) })
    @RequestMapping(value = "/clients",
        method = RequestMethod.POST)
    ResponseEntity<?> clientsPost(@ApiParam(value = "", required = true) @RequestBody Client client);

}
