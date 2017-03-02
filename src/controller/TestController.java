package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chenli
 * @time 2017/3/1 15:53
 */
@Controller
@RequestMapping("/test")
public class TestController{

    @RequestMapping(value = "testDis.go")
    public String testDis(HttpServletRequest request){
       // String shdhj = JSONUtils.toJSONString(new Person("23", "shdhj", 456));
       // System.out.println(shdhj);
        return "hello world";
    }
}

