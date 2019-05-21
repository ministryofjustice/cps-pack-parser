package uk.gov.justice.digital.cpspackparser.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller

public class TestController {
    @RequestMapping("/" )
    public String index() {
        return "test.html";
    }
}
