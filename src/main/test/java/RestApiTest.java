import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.swagger.Swagger2SpringBoot;
import io.swagger.model.AuthenticationRequest;
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
    private String adminToken  = "6d8c9461275d90612a251e6ea6e76567e105746dfd2d2e62e9ef12f669c466af";
    HttpHeaders header = new HttpHeaders();

    @Test
    public void clientTest() {


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
        header.set("Authorization", userToken);




//        Client client = new Client();
//        client.setClientId("test");
//        client.setDescription("test client");
//        client.setDomain("gncloud.io");
//
//        header.add("Authorization", adminToken);
//        request("/clients", "post", client, header);
//        request("/clients", "get", null, header);







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