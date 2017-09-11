import io.swagger.Swagger2SpringBoot;
import io.swagger.api.ClientsApiController;
import io.swagger.model.Client;
import io.swagger.service.ClientService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientsApiController.class)
@ActiveProfiles("test")
public class ClientApiTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void ClientTest() throws Exception {


        // client add
        Client client1 = new Client();
        client1.setDescription("test1");
        client1.setDomain("http://cloud.gncloud.io");

        final ResultActions actualResult;

        actualResult = mockMvc.perform(post("/v1/clients")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andDo(print());



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
