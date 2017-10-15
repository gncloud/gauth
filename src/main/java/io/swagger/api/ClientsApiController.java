package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.Client;
import io.swagger.model.Token;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import io.swagger.service.ClientService;
import io.swagger.service.TokenService;
import io.swagger.service.UserClientScopeService;
import io.swagger.service.UserService;
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

    @Autowired
    private UserService userService;

    public ResponseEntity<?> clientsClientIdDelete(@ApiParam(value = "target client id",required=true ) @PathVariable("clientId") String clientId,
                                                   @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);

            clientService.deleteClient(clientId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("clientsClientIdDelete error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
        }
    }

    public ResponseEntity<?> clientsClientIdGet(@ApiParam(value = "",required=true ) @PathVariable("clientId") String clientId,
                                                @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);

            Client registerClient = clientService.findByClient(clientId);

            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("clientsClientIdGet error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
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
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("clientsClientIdPut error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
        }
    }

    public ResponseEntity<?> clientsGet(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            Token AdminToken = tokenService.isAdminToken(authorization);

            List<Client> registerClients = clientService.selectClients(AdminToken);

            return new ResponseEntity<List<Client>>(registerClients, HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }  catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("clientsGet error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    public ResponseEntity<?> clientsPost(@ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                         @ApiParam(value = "" ,required=true ) @RequestBody Client client) {
        try {
            tokenService.isAdminToken(authorization);

            Client registerClient = clientService.insertClient(client);

            if(registerClient == null){
                throw new ApiException("create client fail");
            }
            return new ResponseEntity<Client>(registerClient, HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("clientsPost error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    public ResponseEntity<?> userClientScopePost(@ApiParam(value = "target",required=true ) @PathVariable("userId") String userId,
                                         @RequestParam(value = "clientId", required = true) String clientId,
                                                 @RequestBody(required = false) UserClientScope userClientScope) {
        try {

            User registerUser = userService.findByUser(userId);
            if(registerUser == null){
                throw new ApiException("not found user");
            }
            Client registerClient = clientService.findByClient(clientId);
            if(registerClient == null){
                throw new ApiException("not found clientId");
            }

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
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("userClientScopePost error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    @Override
    public ResponseEntity<?> clientsClientIdDelete(@ApiParam(value = "target user id", required = true) @PathVariable("userId") String userId,
                                                   @ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                                   @RequestParam(value = "clientId", required = true) String clientId,
                                                   @RequestParam(value = "scopeId", required = true) String scopeId) {
        try {

            tokenService.isAdminToken(authorization);

            UserClientScope userClientScope = new UserClientScope();
            userClientScope.setClientId(clientId);
            userClientScope.setUserId(userId);
            userClientScope.setScopeId(scopeId);
            userClientScopeService.deleteUserScope(userClientScope);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("clientsClientIdDelete error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }
}
