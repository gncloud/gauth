package io.swagger.api;

import io.swagger.model.Client;

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
public class ClientsApiController implements ClientsApi {

    public ResponseEntity<Void> clientsClientIdDelete(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Client> clientsClientIdGet(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId) {
        // do some magic!
        return new ResponseEntity<Client>(HttpStatus.OK);
    }

    public ResponseEntity<Client> clientsClientIdPut(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId,
        @ApiParam(value = ""  ) @RequestBody Client client) {
        // do some magic!
        return new ResponseEntity<Client>(HttpStatus.OK);
    }

    public ResponseEntity<Void> clientsGet() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Client> clientsPost(@ApiParam(value = "" ,required=true ) @RequestBody Client client) {
        // do some magic!
        return new ResponseEntity<Client>(HttpStatus.OK);
    }

}
