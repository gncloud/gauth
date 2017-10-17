
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/*
 * create joonwoo 2017. 9. 18.
 * 
 */
public class StringEncodingTest {

    @Test
    public void m(){
        int time = (int) Calendar.getInstance().getTime().getTime();
        System.out.println(time);


    }


    @Test
    public void encoding(){

        System.out.println(System.currentTimeMillis());

        String target = "1111";

        System.out.println("target : " + target);

        System.out.println(DigestUtils.sha512Hex(target));

        Assert.assertEquals(1,1);

    }



}