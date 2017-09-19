package co.gauth.sample;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * create joonwoo 2017. 9. 19.
 * 
 */
@Controller
public class SampleController {


    @RequestMapping("/pending")
    public String pending(){
        return "pending";
    }

    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }


    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/contents")
    public String contents(){
        return "contents";
    }


}