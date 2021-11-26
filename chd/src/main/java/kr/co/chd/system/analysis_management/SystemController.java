package kr.co.chd.system.analysis_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/chd")
public class SystemController {

    @GetMapping("/envir")
    public ModelAndView reciveEnvirInfo(){
        ModelAndView mav = new ModelAndView("don");
        //mav.addObject("envirInfo", envirInfo);
        return mav;
    }
}
