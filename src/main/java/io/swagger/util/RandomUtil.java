package io.swagger.util;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class RandomUtil {

    /*
     * 길이만큼 랜덤 문자열 생성
     */
    public static String randomString(int length) {
        String randomStr = "";
        for (int i = 0; i < length; i++) {
            int rndVal = (int) (Math.random() * 62);
            if (rndVal < 10) {
                randomStr += rndVal;
            } else if (rndVal > 35) {
                randomStr += (char) (rndVal + 61);
            } else {
                randomStr += (char) (rndVal + 55);
            }
        }
        return randomStr;
    }

}
