package kr.co.system.chd.acess.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chd")
public class AcessController {
    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }
}
