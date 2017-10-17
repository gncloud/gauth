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
        AuthenticationRequest actionUser = new AuthenticationRequest();
        actionUser.setUserId("admin");
        actionUser.setPassword("1111");
        actionUser.setClientId("gauth");
        String response = "";
        try {
            response = request("/tokens", "post", actionUser, null)
                    .getResponse().getContentAsString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userToken = new Gson().fromJson(response ,Map.class).get("tokenId").toString();
        adminToken = userToken;

    }

    private void addClient(String clientId, String desc, String domain){
        Client client = new Client();
        client.setClientId(clientId);
        client.setDescription(desc);
        client.setDomain(domain);
        header.set("Authorization", adminToken);
        request("/clients", "post", client, header);
    }

    private void addScope(String scopeId, String clientId, String isDefault, String desc){
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

    private String signup(String clientId, String userId, String password
                        , String addr, String company, String email
                        , String name, String phone, String activateKey){
        User user = new User();
        user.setPassword(password);
        user.setUserId(userId);
        user.setAddress(addr);
        user.setCompany(company);
        user.setEmail(email);
        user.setName(name);
        user.setPhone(phone);

        String userCode = null;
        String content = null;
        try {
            content = request("/users?clientId="+ clientId +"&activateKey=" + activateKey, "post", user, null)
                            .getResponse().getContentAsString();
            userCode = new Gson().fromJson(content,Map.class).get("userCode").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userCode;
    }

    private String login(String clientId, String userId, String password){
        AuthenticationRequest actionUser = new AuthenticationRequest();
        actionUser.setUserId(userId);
        actionUser.setPassword(password);
        actionUser.setClientId(clientId);
        String content = null;
        String userToken = null;
        try {
            content = request("/tokens", "post", actionUser, null).getResponse().getContentAsString();
            userToken = new Gson().fromJson(content,Map.class).get("tokenId").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return userToken;
    }


    private void tokenGet(String token){
        request("/tokens/"+token, "get", null, null);
    }

    @Test
    public void clientTest() {

        deleteClient("test");
        deletePendUser();
        deleteTestUser();

        adminLogin();

        addClient("test", "test client", "test.co");

        addScope("testScope", "test", "Y","Add testScope");
        addScope("testAdminScope", "test", "N","Add Admin testScope");

        String activateKey = register("test", "test@sample.com");

        String userCode = signup("test", "test@gncloud.co"
                                ,"1234","home","gncloud"
                                , "test@sample.com", "jwkim"
                                , "010-1234-1234", activateKey);



        String token = login("test","test@gncloud.co", "1234");

        tokenGet(token);

        



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
            System.out.println("type:(" + type + ") url : " + url);
            System.out.println("statis : " + resultActions.andReturn().getResponse().getStatus());
            System.out.println("content : " + resultActions.andReturn().getResponse().getContentAsString());
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