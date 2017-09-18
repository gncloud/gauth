package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Client;
import io.swagger.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-08T07:13:42.158Z")

@Controller
public class ClientsApiController implements ClientsApi {

    private static Logger logger = LoggerFactory.getLogger(ClientsApiController.class);

    @Autowired
    private ClientService clientService;

    public ResponseEntity<Void> clientsClientIdDelete(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId) {
        try {
            clientService.deleteClient(clientId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            logger.error("clientsClientIdDelete", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Client> clientsClientIdGet(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId) {
        try {
            Client registerClient = clientService.findByClient(clientId);
            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (Exception e){
            logger.error("clientsClientIdGet", e);
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Client> clientsClientIdPut(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId,
        @ApiParam(value = ""  ) @RequestBody Client client) {
        try {

            client.setClientId(clientId);

            Client registerClient = clientService.updateClient(client);

            if(registerClient == null){
                throw new Exception("update client fail");
            }
            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (Exception e){
            logger.error("clientsClientIdPut", e);
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<Client>> clientsGet() {
        List<Client> registerClients = clientService.selectClients();
        return new ResponseEntity<List<Client>>(registerClients, HttpStatus.OK);
    }

    public ResponseEntity<Client> clientsPost(@ApiParam(value = "" ,required=true ) @RequestBody Client client) {
        try {

            Client registerClient = clientService.insertClient(client);

            if(registerClient == null){
                throw new Exception("create client fail");
            }
            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (Exception e){
            logger.error("clientsPost ", e);
            return new ResponseEntity<Client>(HttpStatus.BAD_REQUEST);
        }
    }

}
