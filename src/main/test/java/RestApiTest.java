import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.swagger.Swagger2SpringBoot;
import io.swagger.model.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/*
 * create joonwoo 2017. 10. 16.
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Swagger2SpringBoot.class})
@WebAppConfiguration
public class RestApiTest {

    org.slf4j.Logger logger = LoggerFactory.getLogger(RestClientApiTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    ObjectMapper mapper = new ObjectMapper();

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    // authorization
    private String adminToken  = "";
    HttpHeaders header = new HttpHeaders();



    private void adminLogin(){
        if(sqlSession == null){
            logger.debug("sqlSession not connection");
        }

        //초기 어드민 로그인
//        AuthenticationRequest actionUser = new AuthenticationRequest();
//        actionUser.setUserId("admin");
//        actionUser.setPassword("1111");
//        actionUser.setClientId("gauth");
        Map<String, String> loginUser = new HashMap<>();
        loginUser.put("userId", "admin");
        loginUser.put("password", "1111");
        loginUser.put("clientId", "gauth");
        String response = "";
        try {
            response = request("/tokens", "post", loginUser, null)
                    .getResponse().getContentAsString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userToken = new Gson().fromJson(response ,Map.class).get("tokenId").toString();
        adminToken = userToken;

    }

    private void clientPost(String clientId, String desc, String domain){
        Client client = new Client();
        client.setClientId(clientId);
        client.setDescription(desc);
        client.setDomain(domain);
        header.set("Authorization", adminToken);
        request("/clients", "post", client, header);
    }

    private void clientPut(String clientId, String desc, String domain){
        Client client = new Client();
        client.setClientId(clientId);
        client.setDescription(desc);
        client.setDomain(domain);
        header.set("Authorization", adminToken);
        request("/clients/" + clientId, "put", client, header);
    }
    private void clientGet(String clientId){
        header.set("Authorization", adminToken);
        request("/clients/" + clientId, "get", null, header);
    }

    private void clientGetAdmin(){
        header.set("Authorization", adminToken);
        request("/clients", "get", null, header);
    }

    private void clientDelete(String clientId){
        header.set("Authorization", adminToken);
        request("/clients/" + clientId, "delete", null, header);
    }


    private void scopePost(String scopeId, String clientId, String isDefault, String desc){
        Scope scope = new Scope();
        scope.setScopeId(scopeId);
        scope.setClientId(clientId);
        scope.setIsDefault(isDefault);
        scope.setDescription(desc);
        header.set("Authorization", adminToken);
        request("/scopes", "post", scope, header);
    }

    private String register(String clientId, String email){
        PendingUserRequest pendingUser = new PendingUserRequest();
        pendingUser.setClientId(clientId);
        pendingUser.setEmail(email);
        String content = null;
        String activateKey = null;
        try {
            content = request("/register", "post", pendingUser, null).getResponse().getContentAsString();
            activateKey = new Gson().fromJson(content,Map.class).get("activateKey").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return activateKey;
    }

    private void registerUserActive(String activateKey){
        request("/activate?activateKey=" + activateKey, "post", null, null);
    }

    private String signup(String clientId, String userId, String password
            , String addr, String company, String email
            , String name, String phone){

        return signup(clientId, userId, password
                , addr, company, email
                , name, phone, "");
    }

    private String signup(String clientId, String userId, String password
                        , String addr, String company, String email
                        , String name, String phone, String activateKey){
        Map<String, String> addUser = new HashMap<>();
        addUser.put("password", password);
        addUser.put("userId", userId);
        addUser.put("addr", addr);
        addUser.put("company", company);
        addUser.put("email", email);
        addUser.put("name", name);
        addUser.put("phone", phone);

        String userCode = null;
        String content = null;
        try {
            content = request("/users?clientId="+ clientId +"&activateKey=" + activateKey, "post", addUser, null)
                            .getResponse().getContentAsString();
            userCode = new Gson().fromJson(content,Map.class).get("userCode").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userCode;
    }

    private String login(String clientId, String userId, String password){
        String content = null;
        String userToken = null;
        Map<String, String> loginUser = new HashMap<>();
        loginUser.put("password", password);
        loginUser.put("userId", userId);
        loginUser.put("clientId", clientId);


        try {
            content = request("/tokens", "post", loginUser, null).getResponse().getContentAsString();
            userToken = new Gson().fromJson(content,Map.class).get("tokenId").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userToken;
    }

    private void userClientScopePost(String clientId, int userCode){
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setUserCode(userCode);
        userClientScope.setClientId(clientId);
        request("/userClientScope", "post", userClientScope, null);
    }


    private void userClientScopeDelete(String clientId, int userCode){
        userClientScopeDelete(clientId, userCode, "");
    }

    private void userClientScopeDelete(String clientId, int userCode, String scopeId){
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setUserCode(userCode);
        userClientScope.setClientId(clientId);
        userClientScope.setScopeId(scopeId);
        request("/userClientScope?userCode=" + userCode
                    + "&clientId=" + clientId
                    + "&scopeId=" + scopeId
                , "delete", null, null);
    }

    private Map<String, Object> myUserGet(String token){

        String content = null;
        Map<String, Object> myUserData = new HashMap<>();

        try {
            header.set("Authorization", token);
            content = request("/user", "get", null, header).getResponse().getContentAsString();
            myUserData.put("client", new Gson().fromJson(content,Map.class).get("userCode").toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return myUserData;
    }

    private void scopesGetAdmin(String clientId){
        header.set("Authorization", adminToken);
        request("/scopes?clientId=" + clientId, "get", null, header);
    }

    private void scopeGet(String clientId, String scopeId){
        header.set("Authorization", adminToken);
        request("/scopes/" + scopeId + "?clientId=" + clientId, "get", null, header);
    }

    private void scopeDelete(String clientId, String scopeId){
        header.set("Authorization", adminToken);
        request("/scopes/" + scopeId + "?clientId=" + clientId, "delete", null, header);
    }

    private void scopePut(String clientId, String scopeId, String isDefault, String desc){
        header.set("Authorization", adminToken);
        Scope scope = new Scope();
        scope.setIsDefault(isDefault);
        scope.setDescription(desc);
        scope.setScopeId("updateScopeId");
        request("/scopes/" + scopeId + "?clientId=" + clientId, "delete", scope, header);
    }

    private void tokenDelete(String token){
        header.set("Authorization", token);
        request("/token", "delete", null, header);
    }


    private Map<String, String> myTokenGet(String token){
        String content = null;
        Map<String, String> loginData = new HashMap<>();
        try {
            header.set("Authorization", token);
            content = request("/token", "get", null, header).getResponse().getContentAsString();
            loginData.put("tokenId", new Gson().fromJson(content,Map.class).get("tokenId").toString());
            loginData.put("userCode", new Gson().fromJson(content,Map.class).get("userCode").toString());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return loginData;
    }

    private void usersPut(String tokenId, int userCode
                        , String password, String email
                        , String name,String phone
                        , String addr, String company){

        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(addr);
        user.setCompany(company);
        header.set("Authorization", tokenId);
        request("/users/" + userCode, "put", user, header);
    }

    private void usersGetAdmin(int userCode){
        header.set("Authorization", adminToken);
        request("/users/" + userCode, "get", null, header);
    }

    private void pendUsersGetAdmin(){
        header.set("Authorization", adminToken);
        request("/users?state=pending" , "get", null, header);
    }


    private void deleteAdminByUser(String clientId, int userCode){
        header.set("Authorization", adminToken);
        request("/users/" + userCode + "?clientId=" + clientId, "delete", null, header);
    }


    private void deleteEmailByPendUser(String clientId, String email){
        header.set("Authorization", adminToken);
        request("/users?state=pending&email=" + email + "&clientId=" + clientId, "delete", null, header);
    }
    private void deleteActivateKeyByPendUser(String clientId, String activateKey){
        header.set("Authorization", adminToken);
        request("/users?state=pending&activateKey=" + activateKey + "&clientId=" + clientId, "delete", null, header);
    }
    private void deleteAllPendUser(){
        header.set("Authorization", adminToken);
        request("/users?state=pending&truncate=true", "delete", null, header);
    }


    private void selectAdminByUsers(String search, int pageNum){
        header.set("Authorization", adminToken);
        request("/users?search=" + search + "&p=" + pageNum, "get", null, header);
    }


    private void selectClientByUsersGetAdmin(String clientId){
        header.set("Authorization", "72e0b55925e8e70bac9fb0a22a4a428aa3a066a59c15ede5b0b0d91a8616835c");
        request("/users?clientId=" + clientId , "get", null, header);
    }

    @Test
    public void findClientByUser(){
        selectClientByUsersGetAdmin("test");
    }





    @Test
    public void clientTest() {

        deleteClient("test");
        deleteClient("test_two_client");
        deleteTestUser();

        adminLogin();

        clientPost("test", "test client", "test.co");
        clientPut("test", "update client desc", "gncloud");
        clientGet("test");
        clientGetAdmin();


        scopePost("testScope", "test", "Y","Add testScope");
        scopePost("testAdminScope", "test", "N","Add Admin testScope");

        String activateKey = register("test", "test@sample.com");

        pendUsersGetAdmin();


        registerUserActive(activateKey);

        String userCode = signup("test", "test@gncloud.co"
                                ,"1234","home","gncloud"
                                , "test@sample.com", "jwkim"
                                , "010-1234-1234");


        String token = login("test","test@gncloud.co", "1234");

        // tokenId, userCode
        Map<String, String> loginData = myTokenGet(token);
        loginData.put("userCode", loginData.get("userCode").substring(0, loginData.get("userCode").indexOf(".")));


        clientPost("test_two_client", "test desc", "abcd.co");


        userCode = loginData.get("userCode");
        int parseUserCode = Integer.parseInt(userCode);
        userClientScopePost("test_two_client", parseUserCode);


        Map<String, Object> myInfo = myUserGet(loginData.get("tokenId"));


        userClientScopeDelete("test", parseUserCode);


        scopesGetAdmin("test");

        scopeGet("test", "testScope");

        scopePut("test","testScope", "0", "update scopeId");

        scopeDelete("test", "testScope");

        usersPut(loginData.get("tokenId"), parseUserCode, "","kkkkk@gncloud.kr", "update name", "010-2222-1111", "home!", "gauth");

        usersGetAdmin(parseUserCode);

        selectAdminByUsers("60",1);

        deleteEmailByPendUser("test", register("test", "test1@test.co"));
        deleteActivateKeyByPendUser("test", register("test", "test2@test.co"));
        register("test", "test3@test.co");
        deleteAllPendUser();

        deleteAdminByUser("test", parseUserCode);
        tokenDelete(loginData.get("tokenId"));
    }






    public MvcResult request(String url, String type, Object body, HttpHeaders headers) {
        ResultActions resultActions = null;
        try {
            MockHttpServletRequestBuilder mockMvcBuilder = null;

            if ("post".equals(type)) {
                mockMvcBuilder = post(url);
            } else if ("put".equals(type)) {
                mockMvcBuilder = put(url);
            } else if ("delete".equals(type)) {
                mockMvcBuilder = delete(url);
            } else if ("get".equals(type)) {
                mockMvcBuilder = get(url);
            } else if ("head".equals(type)) {
                mockMvcBuilder = head(url);
            } else {
                throw new Exception("type Exception");
            }


            ObjectMapper objectMapper = new ObjectMapper();
            String userJson = objectMapper.writeValueAsString(body);
            //Gson gson = new Gson();

            if(headers != null){
                resultActions = this.mockMvc.perform(mockMvcBuilder
                        .contentType(contentType)
                        .headers(headers)
                        .content(userJson));
            }else{
                resultActions = this.mockMvc.perform(mockMvcBuilder
                        .contentType(contentType)
                        .content(userJson));
            }


            //resultActions.andDo(print());
            System.out.println("------------------------------------------------------------------------------------------------");
            System.out.println("request: type:(" + type + ") url : " + url);
            System.out.println("request body : " + (userJson == null ? "" : userJson));


            System.out.println("response statis : " + resultActions.andReturn().getResponse().getStatus());
            System.out.println("response content : " + resultActions.andReturn().getResponse().getContentAsString());
            System.out.println("------------------------------------------------------------------------------------------------");
            return resultActions.andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }




    private void deleteClient(String id){
        try {
            dataSource.getConnection().createStatement().execute("delete from Token where client_id = '" + id + "'");
            dataSource.getConnection().createStatement().execute("delete from Scope where client_id = '" + id + "'");
            dataSource.getConnection().createStatement().execute("delete from UserClientScope where client_id = '" + id + "'");
            dataSource.getConnection().createStatement().execute("delete from Client where client_id = '" + id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void deletePendUser(){
        try {
            dataSource.getConnection().createStatement().execute("delete from Pending");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTestUser(){
        try {
            dataSource.getConnection().createStatement().execute("delete from User where user_id='test@gncloud.co'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}