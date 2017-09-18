
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

/*
 * create joonwoo 2017. 9. 18.
 * 
 */
public class StringEncodingTest {

    @Test
    public void encoding(){

        String target = "gncloud";

        System.out.println("target : " + target);

        System.out.println(DigestUtils.sha512Hex(target));

        Assert.assertEquals(1,1);

    }



}