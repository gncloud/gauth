import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;


public class MockTest {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(MockTest.class);

    @Test
    public void mockTest(){

        Date nowDate = Calendar.getInstance().getTime();
        String formatNowDate = new SimpleDateFormat("yyyy-M-d H:m:s").format(nowDate);

        System.out.println("nowTime => " + nowDate);

        System.out.println("formatNowDate => " + formatNowDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.MINUTE, 60);
        Date appendDate = calendar.getTime();

        String formatAppendDate = new SimpleDateFormat("yyyy-M-d H:m:s").format(appendDate);
        System.out.println("appendDate => " + appendDate);
        System.out.println("formatNowDate => " + formatAppendDate);

    }





}
