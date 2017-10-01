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

    /*
     * admin 인덱스 페이지 이동
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String home(){
        return "forward:admin/index.html";
    }

    @RequestMapping(value = "/admin/client", method = RequestMethod.GET)
    public String client() {
        logger.debug("client call");
        return "admin/client";
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String user() {
        logger.debug("user call");
        return "forward:admin/user.html";
    }

    @RequestMapping(value = "/admin/wait-user", method = RequestMethod.GET)
    public String waitUser(){
        logger.debug("waitUser call");
        return "forward:admin/wait-user.html";
    }
}