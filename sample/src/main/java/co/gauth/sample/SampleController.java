package co.gauth.sample;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * create joonwoo 2017. 9. 19.
 * 
 */
@Controller
public class SampleController {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(SampleController.class);

    @RequestMapping("/pending")
    public String pending(){

        logger.debug("pending controller call");

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