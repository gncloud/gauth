package io.swagger.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /*
     * 요청시 날짜와 시간을 리턴
     */
    public static Date requestDate(){
        return Calendar.getInstance().getTime();
    }

    /*
     * 기준 시간에서 분 추가
     */
    public static Date appendDate(Date date, int timeout){
        Calendar appendCalendar = Calendar.getInstance();
        appendCalendar.setTime(date);
        appendCalendar.add(Calendar.MINUTE, timeout);
        return appendCalendar.getTime();
    }

    /*
     * 날짜 데이터 형식 변환
     */
    public static String dateFormat(Date date){
        return new SimpleDateFormat("yyyy-M-d H:m:s").format(date);
    }

    /*
     * 만료 여부
     */
    public static boolean isExpireDate(String expireDate) {
        try {
            // 토큰 만료 시간 확인
            Date expiredDate = new SimpleDateFormat("yyyy-M-d H:m:s").parse(expireDate);

            Date nowDate = Calendar.getInstance().getTime();
            int compare = nowDate.compareTo(expiredDate);

            // 현재보다 미래 시간에 만료 될 경우
            return compare < 0;
        } catch(ParseException e){
            logger.error("expiredDate ParseException", e);
        }
        return false;
    }

}
