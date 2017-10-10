package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Client;
import io.swagger.model.UserClientScope;
import io.swagger.service.ClientService;
import io.swagger.service.TokenService;
import io.swagger.service.UserClientScopeService;
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
public class ClientsApiController implements ClientsApi {

    private static Logger logger = LoggerFactory.getLogger(ClientsApiController.class);

    @Autowired
    private ClientService clientService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserClientScopeService userClientScopeService;

    public ResponseEntity<?> clientsClientIdDelete(@ApiParam(value = "target client id",required=true ) @PathVariable("clientId") String clientId,
                                                   @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);

            clientService.deleteClient(clientId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("clientsClientIdDelete", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> clientsClientIdGet(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId,
                                                @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);

            Client registerClient = clientService.findByClient(clientId);

            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("clientsClientIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> clientsClientIdPut(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId,
                                                @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                                @ApiParam(value = ""  ) @RequestBody Client client) {
        try {
            tokenService.isAdminToken(authorization);

            client.setClientId(clientId);
            Client registerClient = clientService.updateClient(client);

            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("clientsClientIdPut", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> clientsGet(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);

            List<Client> registerClients = clientService.selectClients();

            return new ResponseEntity<List<Client>>(registerClients, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("clientsGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> clientsPost(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                         @ApiParam(value = "" ,required=true ) @RequestBody Client client) {
        try {
            tokenService.isAdminToken(authorization);

            Client registerClient = clientService.insertClient(client);

            if(registerClient == null){
                throw new Exception("create client fail");
            }
            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (AccessControlException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            logger.error("clientsPost", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> userClientScopePost(@ApiParam(value = "target",required=true ) @PathVariable("userId") String userId,
                                         @RequestParam(value = "clientId", required = true) String clientId,
                                                 @RequestBody(required = false) UserClientScope userClientScope) {
        try {
            List<UserClientScope> registerUserClientScopeList = null;
            if(userClientScope != null
                    && userClientScope.getScopeId() != null
                    && !"".equals(userClientScope.getScopeId())){
                userClientScope.setUserId(userId);
                userClientScope.setClientId(clientId);
                registerUserClientScopeList = userClientScopeService.insertUserScope(userClientScope);
            }else{
                userClientScope = new UserClientScope();
                userClientScope.setUserId(userId);
                userClientScope.setClientId(clientId);
                registerUserClientScopeList = userClientScopeService.insertUserClientScope(userClientScope);
            }
            return new ResponseEntity<List<UserClientScope>>(registerUserClientScopeList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("userClientScopePost", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> clientsClientIdDelete(@ApiParam(value = "target user id", required = true) @PathVariable("userId") String userId,
                                                   @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                                   @RequestParam(value = "clientId", required = true) String clientId,
                                                   @RequestParam(value = "scopeId", required = true) String scopeId) {
        try {
            UserClientScope userClientScope = new UserClientScope();
            userClientScope.setClientId(clientId);
            userClientScope.setUserId(userId);
            userClientScope.setScopeId(scopeId);
            userClientScopeService.deleteUserScope(userClientScope);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("userClientScopePost", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
