import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.Swagger2SpringBoot;
import io.swagger.model.Client;
import io.swagger.model.Scope;
import org.junit.Assert;
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

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Swagger2SpringBoot.class})
@WebAppConfiguration
public class RestClientApiTest {


    org.slf4j.Logger logger = LoggerFactory.getLogger(RestClientApiTest.class);

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    ObjectMapper mapper = new ObjectMapper();

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    public MvcResult request(String url, String type, Object body) {
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

            resultActions = this.mockMvc.perform(mockMvcBuilder
                    .contentType(contentType)
                    .content(userJson));

            //resultActions.andDo(print());
            return resultActions.andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
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

            resultActions = this.mockMvc.perform(mockMvcBuilder
                    .contentType(contentType)
                    .headers(headers)
                    .content(userJson));
            //resultActions.andDo(print());
            return resultActions.andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }

    private String responseContentBody(MvcResult mvcResult){
        try {
            return mvcResult.getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            return new String();
        }
    }
    private String objectParsor(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //ignore
            return new String();
        }
    }

    private void resultPrint(MvcResult mvcResult){
        int responseStatus = 0;
        String content = new String();
        responseStatus  = mvcResult.getResponse().getStatus();
        content         = responseContentBody(mvcResult);
        System.out.println("status code"+ responseStatus);
        System.out.println("status body"+ content);
        Assert.assertEquals("post request status : ", responseStatus, 200);
    }

    private void resultPrint(MvcResult mvcResult, Object requestBody){
        int responseStatus = 0;
        String content = new String();
        responseStatus  = mvcResult.getResponse().getStatus();
        content         = responseContentBody(mvcResult);
        System.out.println("status code"+ responseStatus);
        System.out.println("status body"+ content);
        Assert.assertEquals("post request status : ", responseStatus, 200);
        Assert.assertEquals("get request body data", objectParsor(requestBody), content);
    }

    @Test
    public void clientTest() {

        // 클라이언트 정보
        Client client = new Client();
        client.setClientId("gncloud");
        client.setDescription("지앤클라우드");
        client.setDomain("http://cloud.gncloud.io");

        MvcResult mvcResult = null;

        // 등록
        mvcResult = request("/clients", "post", client);
        resultPrint(mvcResult, client);
//
//        // 조회
//        mvcResult = request("/clients/gncloud","get", null);
//        resultPrint(mvcResult);
//
//
//        // 수정
//        client = new Client();
//        client.setDescription("지앤클라우드 수정");
//        client.setDomain("gncloud");
//        mvcResult = request("/clients/gncloud","put", client);
//        resultPrint(mvcResult, client);

        // 삭제
        //mvcResult = request("/clients/gncloud","delete", null);
        //resultPrint(mvcResult);

    }

    @Test
    public void scopeTest() {
        // 스코프 등록 정보
        Scope scope = new Scope();
        scope.setClientId("gncloud");
        scope.setDescription("user desc");
        scope.setScopeId("user");
        scope.setIsDefault("Y");

//        등록
        request("/scopes", "post", scope);

//        디폴트 변경 추가
        scope.setClientId("gncloud");
        scope.setDescription("admin desc");
        scope.setScopeId("admin");
        scope.setIsDefault("N");
        request("/scopes", "post", scope);

//        스코프조회
        request("/scopes/user1?client=gncloud", "get", null);

//        클라이언트의  전체 스코프 조회
        Client client1 = new Client();
        client1.setClientId("gncloud");
        request("/scopes", "get", client1);

//         디스크립션 수정
        scope.setDescription("update user2 desc");
        scope.setClientId("Yi5nbmNsb3VkLmlv");
        request("/scopes/user1", "put", scope);

//        스코프 삭제
        scope = new Scope();
        scope.setClientId("Yi5nbmNsb3VkLmlv");
        request("/scopes/user2", "delete", scope);
    }

    @Test
    public void userTest(){

         //회원가입 클라이언트의 디폴트 스코프가 있을 경우
//        User user = new User();
//        user.setUserId("deleteUser");
//        user.setPassword("security");
//        user.setClientId("Yi5nbmNsb3VkLmlv");
//        user.setAddress("address");
//        user.setPhone("01089982010");
//        user.setEmail("jwkim@gncloud.kr");
//        user.setName("jwkim");
//        user.setCompany("gncloud");
//        request("/users", "post", user);

        // 회원가입 클라이언트의 디폴트 스코프가 없을 경우
//        User user = new User();
//        user.setUserId("joon2");
//        user.setPassword("security");
//        user.setClientId("YS5nbmNsb3VkLmlv");
//        user.setAddress("address");
//        user.setPhone("01089982010");
//        user.setEmail("jwkim@gncloud.kr");
//        user.setName("jwkim");
//        user.setCompany("gncloud");
//        request("/users", "post", user);

        //전체 검색 조회
        //request("/users", "get", null);
        //request("/users?search=joon2", "get", null);

        //user 정보 수정
//        User user = new User();
//        user.setPassword("test");
//        user.setAddress("aaaa");
//        user.setPhone("01012341234");
//        user.setEmail("test@gncloud.kr");
//        user.setName("joonwoo");
//        user.setCompany("com");
//        request("/users/joon2","put", user);




//        User user = new User();
//        user.setUserId("deleteUser1");
//        user.setPassword("security");
//        user.setClientId("Yi5nbmNsb3VkLmlv");
//        user.setAddress("address");
//        user.setPhone("01089982010");
//        user.setEmail("jwkim@gncloud.kr");
//        user.setName("jwkim");
//        user.setCompany("gncloud");
//        request("/users/joon2","post", user);



//        request("/users/deleteUser2","delete", null);



        // 토큰 생성
//        User user1 = new User();
//        user1.setUserId("deleteUser1");
//        user1.clientId("Yi5nbmNsb3VkLmlv");
//        user1.setPassword("aaaev");
//        request("/token", "post", user1);
//
//        User user2 = new User();
//        user2.setUserId("deleteUser2");
//        user2.clientId("Yi5nbmNsb3VkLmlv");
//        user2.setPassword("tseaet");
//        request("/token", "post", user2);


        // 토큰 조회
        //a910e142e5fb564474ed79a1212c51ee77dca7ca4388d0c84e18169ae046326f
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("authorization","beab71412a09cc26eb62b700badfea0c86e38ec50f37ebe649adccdb0b88eff9");
//        request("/token", "get", null, httpHeaders);

        // 토큰 삭제
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("authorization","beab71412a09cc26eb62b700badfea0c86e38ec50f37ebe649adccdb0b88eff9");
//        request("/token", "delete", null, httpHeaders);



        // 특정 클라이언트
        // 개인 유저
        //request("/user/deleteUser1?clientId=clientId", "delete", null);

        //토근 전체 조회
        //request("/tokens", "get", null);

        // 토큰 삭제
        //request("/tokens/1d3f41ed2dfdf2d2a5725d8c04b969f4ba4295e615526d0db2e647e49ee065dc", "delete", null);
        // 토큰 삭제
//        request("/tokens/e3b24cbe0909fa33000fb399a28cea3185b18938efe486a4e922ce9f45752df9", "get", null);

        // 토큰 유효성 체크
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("authorization","e3b24cbe0909fa33000fb399a28cea3185b18938efe486a4e922ce9f45752df9");
//        request("/validate", "head", null, httpHeaders);

    }

}
