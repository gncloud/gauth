import io.swagger.Swagger2SpringBoot;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/*
 * create joonwoo 2017. 10. 16.
 * 
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Swagger2SpringBoot.class})
@WebAppConfiguration
public class RestApiTest {


}