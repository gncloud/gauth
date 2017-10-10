import org.junit.Assert;
import org.junit.Test;

public class RegexTest {

    @Test
    public void regexpTest(){

        String reg = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";


        String[] email = {"abc@test.com",
                "_abc@test.com",
                "123aerg@1243test.aa",
                "AEGR34t-45y@42y5h.aa",
                "wh4srth@hjhh.com",
                "aegr.aeg@gmail.com"
        };


        for(int i=0; i< email.length;i++){
            System.out.println("email : " + email[i]);
            Assert.assertEquals(email[i].matches(reg),true);
        }




    }

}
