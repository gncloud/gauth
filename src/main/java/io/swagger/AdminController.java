package io.swagger;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * create joonwoo 2017. 9. 22.
 * 
 */
@Controller
public class AdminController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AdminController.class);
    @RequestMapping(value = "/admin", method = RequestMethod.GET)

    /*
     * admin 인덱스 페이지 이동
     */
    public String home(){
        return "forward:admin/index.html";
    }
}