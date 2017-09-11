import io.swagger.model.Client;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.net.URI;

public class ClientApiTest {


    org.slf4j.Logger logger = LoggerFactory.getLogger(ClientApiTest.class);




    private void post(String url, Client client){
        ResponseEntity responseEntity = null;
        try {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Client> httpEntity = new HttpEntity<>(client, httpHeaders);

            RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate.exchange(new URI(url)
                    , HttpMethod.POST
                    , httpEntity
                    , Client.class);

            responsePrint(responseEntity);
        }catch (Exception e) {
            logger.error("post api request error ", e);
        }
    }

    private void responsePrint(ResponseEntity responseEntity){

        if(responseEntity != null){
            System.out.println(responseEntity.getStatusCode());
            System.out.println(responseEntity.getBody().toString());
        }else{
            System.out.println("responsePrint null Pointer");
        }
    }


    @Test
    public void ClientTest() throws Exception {

        Client client1 = new Client();
        client1.setDescription("test");
        client1.setDomain("http://cloud.gncloud.io");
        client1.setIsDefault("user");

        post("http://localhost:8080/v1/clients", client1);





//        final ResultActions actualResult;
//        actualResult = mockMvc.perform(post("/v1/clients")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .accept(MediaType.APPLICATION_JSON_UTF8)
//        ).andDo(print());



//post        http://localhost:8080/v1/clients
//        {
//            "description":"test",
//                "domain":"test.com"
//        }

//put        http://localhost:8080/v1/clients/WWt1bFc5VTBLTA
//        {
//            "domain":"next.com",
//                "description":"ok"
//        }

//GET        http://localhost:8080/v1/clients/WWt1bFc5VTBLTA

//DELETE        http://localhost:8080/v1/clients/WWt1bFc5VTBLTA



    }


}
