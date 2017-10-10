package io.swagger;

import io.swagger.service.TokenService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.AccessControlException;
import java.util.Arrays;
import java.util.List;

/*
 * create joonwoo 2017. 9. 22.
 * 
 */
@Controller
public class AdminController {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String home() {
        return "admin/login";
    }

    @RequestMapping("admin/client")
    public String client(HttpServletRequest request, HttpServletResponse response){
        return "admin/client";
    }

    @RequestMapping("admin/user")
    public String user(HttpServletRequest request, HttpServletResponse response){
        return "admin/user";
    }

    @RequestMapping("admin/pendusers")
    public String waitUser(HttpServletRequest request, HttpServletResponse response){
        return "admin/pendusers";
    }
}