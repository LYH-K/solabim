package kr.co.chd.system.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/chd")
public class AccessController {
    @Autowired
    AccessService accessService;

    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(Manager manager){
        accessService.login(manager);
        return new ModelAndView("login");
    }
}
