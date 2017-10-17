package io.swagger.api;

import io.swagger.annotations.ApiParam;
import io.swagger.model.*;
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
import java.util.*;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.SpringCodegen", date = "2017-09-17T06:54:37.818Z")

@Controller
public class UsersApiController implements UsersApi {

    private static Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserClientScopeService userClientScopeService;

    public ResponseEntity<?> usersGet(@ApiParam(value = "admin token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                      @ApiParam(value = "search keyword") @RequestParam(value = "search", required = false) String search,
                                      @ApiParam(value = "current page") @RequestParam(value = "p", required = false) String p,
                                      @ApiParam(value = "state") @RequestParam(value = "state", required = false) String state) {
        try {
            Token adminToken = tokenService.isAdminToken(authorization);

            if(state != null && UserService.PENDING_STATUS.equals(state)){
                List<PendingUserResponse> pendUserList = userService.findByPendingUserInfoList(adminToken);
                return new ResponseEntity<List<PendingUserResponse>>(pendUserList, HttpStatus.OK);
            }else {
                Map<String, Object> result = new HashMap<>();
                Map<String, String> searchMap = new HashMap<>();
                searchMap.put("search", search);
                searchMap.put("adminToken", adminToken.getTokenId());
                int page = (Integer.parseInt(p) - 1) * 20;
                searchMap.put("p", String.valueOf(page));
                List<User> registerUserList = userService.findByUsers(searchMap);
                int userCount = userService.selectUserCount(searchMap);

                Map<String, Object> responsePageInfo = new HashMap<>();
                responsePageInfo.put("p", p);
                responsePageInfo.put("userCount", userCount);
                result.put("pageInfo", responsePageInfo);

                int userSize = registerUserList.size();
                ArrayList<String> searchUserList = new ArrayList<>();
                for(int i=0; i < userSize; i++){
                    searchUserList.add(registerUserList.get(i).getUserId());
                }
                List<UserClientScope> userClientScopeList = userClientScopeService.findByUserSearchList(searchUserList);

                List<Map<String, Object>> userClientMappingList = new ArrayList<>();
                int userClientSize = userClientMappingList.size();
                int userClientScopeListSize = userClientScopeList.size();
                for(int i=0; i<userSize; i++){
                    Map<String, Object> appendUser = new HashMap<>();
                    appendUser.put("userId",       registerUserList.get(i).getUserId());
                    appendUser.put("address",      registerUserList.get(i).getAddress());
                    appendUser.put("company",      registerUserList.get(i).getCompany());
                    appendUser.put("email",        registerUserList.get(i).getEmail());
                    appendUser.put("name",         registerUserList.get(i).getName());
                    appendUser.put("phone",        registerUserList.get(i).getPhone());
                    appendUser.put("registerDate", registerUserList.get(i).getRegisterDate());
                    List<UserClientScope> scopeList = new ArrayList<>();
                    for(int j=0; j < userClientScopeListSize; j++){
                        String userId = registerUserList.get(i).getUserId();
                        if(userId.equals(userClientScopeList.get(j).getUserCode())){
                            UserClientScope tempUserClientScope = new UserClientScope();
                            tempUserClientScope.setClientId(userClientScopeList.get(j).getClientId());
                            tempUserClientScope.setScopeId(userClientScopeList.get(j).getScopeId());
                            scopeList.add(tempUserClientScope);
                        }
                    }
                    appendUser.put("scopeList", scopeList);
                    userClientMappingList.add(appendUser);
                }
                result.put("userList", userClientMappingList);

                return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
            }
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
                logger.error("usersGet error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    public ResponseEntity<?> usersPost(@ApiParam(value = "client id", required = true) @RequestParam(value = "clientId", required = true) String clientId,
                                       @ApiParam(value = "" ,required=true ) @RequestBody User user,
                                       @ApiParam(value = "activateKey") @RequestParam(value = "activateKey", required = false) String activateKey) {
        try {

            User registerUser = userService.signup(user, activateKey, clientId);
            if(registerUser.getToken() == null || "".equals(registerUser.getToken())){
                registerUser.setToken("");
            }
            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
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
                logger.error("usersPost error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    public ResponseEntity<?> usersUserIdDelete(@ApiParam(value = "delete target", required = true) @PathVariable("userCode") int userCode,
                                               @ApiParam(value = "User Authentication BEARER Token", required = true) @RequestHeader(value = "Authorization", required = true) String authorization,
                                               @ApiParam(value = "client id" ,required=true ) @RequestParam String clientId,
                                               @ApiParam(value = "state" ,required=false ) @RequestParam String state,
                                               @ApiParam(value = "truncate" ,required=false ) @RequestParam String truncate,
                                               @ApiParam(value = "activateKey" ,required=false ) @RequestParam String activateKey,
                                               @ApiParam(value = "email" ,required=false ) @RequestParam String email) {
        try {

            if( state != null && UserService.PENDING_STATUS.equals(state) ){
                if(email != null && !"".equals(email)){
                    userService.deleteEmailByPendUser(email);
                }else if(activateKey != null && !"".equals(activateKey)){
                    userService.deletePendUserActivateKey(activateKey);
                }else if(truncate != null && "true".equals(truncate)){
                    userService.deleteAllPendUser();
                }else{
                    //email, activateKey, truncate 요청이 없을 경우
                    throw new ApiException("using [email|activateKey|truncate]");
                }
            }else if(clientId != null && !"".equals(clientId)){

                userService.deleteUser(userCode, clientId);
            }else{
                throw new ApiException("invalid state");
            }

            return new ResponseEntity<>(HttpStatus.OK);
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
                logger.error("usersUserIdDelete error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.toString()), httpStatus);
        }
    }

    public ResponseEntity<?> usersUserIdGet(@ApiParam(value = "search userId",required=true ) @PathVariable("userCode") String userCode,
                                            @ApiParam(value = "admin token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization) {
        try {
            tokenService.isAdminToken(authorization);
            User registerUser = userService.getUser(userCode);

            Map<String, Object> result = new HashMap<>();
            result.put("userInfo", registerUser);

            List<UserClientScope> userClientScopes = userClientScopeService.selectUserMappingList(registerUser);

            result.put("userInfo", registerUser);
            result.put("userClientScopes", userClientScopes);

            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        } catch (AccessControlException e){
            logger.warn("AccessControlException {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            logger.error("usersUserIdGet", e);
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(1, e.toString()), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> usersUseridPut(@ApiParam(value = "search userId",required=true ) @PathVariable("userId") String userId,
                                            @ApiParam(value = "user token" ,required=true ) @RequestHeader(value="Authorization", required=true) String authorization,
                                            @RequestBody User user) {
        try {
            user.setUserId(userId);
            User registerUser = userService.updateUser(user);
            return new ResponseEntity<User>(registerUser, HttpStatus.OK);
        } catch (Exception e){
            HttpStatus httpStatus;
            if(e instanceof ApiException){
                httpStatus = HttpStatus.BAD_REQUEST;
                logger.warn("bad request {}", e.getMessage());
            }else{
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                logger.error("usersUseridPut error", e);
            }
            return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(ApiResponseMessage.ERROR, e.getMessage()), httpStatus);
        }
    }


}
