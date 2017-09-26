import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.swagger.Swagger2SpringBoot;
import io.swagger.model.*;
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


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Swagger2SpringBoot.class})
@WebAppConfiguration
public class RestClientApiTest {

    org.slf4j.Logger logger = LoggerFactory.getLogger(RestClientApiTest.class);


    @Autowired
    private DataSource dataSource;

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
    private String adminToken  = "52eed95c98e6317803b33cd9c9232a111ec7b97795567b8f849c76cdba50aece";

    HttpHeaders httpHeaders = new HttpHeaders();

    HttpHeaders userHeader = new HttpHeaders();

    @Test
    public void createClientTest() {

        //test
        tokenDelete("test");
        pendingDelete("test@test.com");
        clientDelete("test");
        userDelete("test");


        // 클라이언트

        Client client = new Client();
        client.setClientId("gncloud");
        client.setDescription("test client");
        client.setDomain("gncloud.io");

        httpHeaders.add("Authentication", adminToken);
        request("/clients", "post", client, httpHeaders);
        request("/clients", "get", null, httpHeaders);


        Scope scope = new Scope();
        scope.setScopeId("test_user");
        scope.setClientId(client.getClientId());
        scope.setIsDefault("N");
        scope.setDescription("test");
        request("/scopes", "post", scope, httpHeaders);
        request("/scopes/" + scope.getScopeId() + "?clientId=" + client.getClientId(), "get", null, httpHeaders);

        Scope scope2 = new Scope();
        scope2.setScopeId("test_user2");
        scope2.setClientId(client.getClientId());
        scope2.setIsDefault("y");
        scope2.setDescription("test2");
        request("/scopes", "post", scope2, httpHeaders);
        Scope scope3 = new Scope();
        scope3.setScopeId("test_user3");
        scope3.setClientId(client.getClientId());
        scope3.setIsDefault("Y");
        scope3.setDescription("test3");
        request("/scopes", "post", scope3, httpHeaders);
        Scope scope4 = new Scope();
        scope4.setScopeId("test_user4");
        scope4.setClientId(client.getClientId());
        scope4.setIsDefault("n");
        scope4.setDescription("test4");
        request("/scopes", "post", scope4, httpHeaders);
        request("/scopes?client=" + client.getClientId(), "get", null, httpHeaders);




        PendingUserRequest pendingUser1 = new PendingUserRequest();
        pendingUser1.setClientId(client.getClientId());
        pendingUser1.setEmail("test@test.com");
        String content = null;
        String activateKey = null;

        try {
            content = request("/activates", "post", pendingUser1, null).getResponse().getContentAsString();
            activateKey = new Gson().fromJson(content,Map.class).get("activateKey").toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        User user = new User();
        user.setPassword("1111");
        user.setUserId("test");
        user.setAddress("abc");
        user.setCompany("com");
        user.setEmail("test@test.com");
        user.setName("kk");
        user.setPhone("123123");
        request("/users?clientId="+ client.getClientId() +"&activateKey=" + activateKey, "post", user, null);

        request("/userClientScope/" + user.getUserId() + "?clientId="+ client.getClientId(), "post", user, null);


        AuthenticationRequest actionUser = new AuthenticationRequest();
        actionUser.setUserId("test");
        actionUser.setPassword("1111");
        actionUser.setClientId(client.getClientId());

        try {
            content = request("/tokens", "post", actionUser, null).getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userToken = new Gson().fromJson(content,Map.class).get("tokenId").toString();
        userHeader.set("Authentication", userToken);

        request("/tokens/" + userToken, "get", null, null);

        request("/token", "delete", null, userHeader);

        request("/users/" + user.getUserId() + "?client=" + client.getClientId(), "delete", null, userHeader);


        request("/scopes/" + scope.getScopeId() + "?clientId=" + client.getClientId(), "delete", null, httpHeaders);
        request("/scopes/" + scope2.getScopeId() + "?clientId=" + client.getClientId(), "delete", null, httpHeaders);
        request("/scopes/" + scope3.getScopeId() + "?clientId=" + client.getClientId(), "delete", null, httpHeaders);
        request("/scopes/" + scope4.getScopeId() + "?clientId=" + client.getClientId(), "delete", null, httpHeaders);
        request("/scopes?client=" + client.getClientId(), "get", null, httpHeaders);


        client.setDescription("update1");
        client.setDomain("update.gncloud.io");
        request("/clients/" + client.getClientId(), "put", client, httpHeaders);
        request("/clients/" + client.getClientId(), "get", null, httpHeaders);

        request("/clients/" + client.getClientId(), "delete", null, httpHeaders);
        request("/clients/" + client.getClientId(), "get", null, httpHeaders);
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



    @Test
    public void deleteTest(){



        pendingDelete("test@test.com");
        clientDelete("test");
        userDelete("test");


    }

    private void pendingDelete(String email){
        try {
            dataSource.getConnection().createStatement().execute("delete from Pending where email = '" + email + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clientDelete(String id){
        try {
            dataSource.getConnection().createStatement().execute("delete from UserClientScope where user_id = '" + id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void userDelete(String id){
        try {
            dataSource.getConnection().createStatement().execute("delete from User where user_id = '" + id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tokenDelete(String id){
        try {
            dataSource.getConnection().createStatement().execute("delete from Token where user_id = '" + id + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
