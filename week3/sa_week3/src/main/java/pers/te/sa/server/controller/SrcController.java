package pers.te.sa.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class SrcController implements Control {

    @RequestMapping(value = "/emailService", method = RequestMethod.GET)
    public String emailService(){
        return "EmailService";
    }
}
