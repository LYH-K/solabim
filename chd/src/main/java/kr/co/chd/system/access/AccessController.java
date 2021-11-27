package kr.co.chd.system.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/chd")
public class
AccessController {
    @Autowired
    AccessService accessService;

    @GetMapping("/login")
    public ModelAndView loginform(){
        return new ModelAndView("chd/login");
    }

    @PostMapping("/login")
    public ModelAndView login(Manager manager, HttpSession session){
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/chd/list"));

        if("".equals(manager.getId()) || "".equals(manager.getPassword())){
            return new ModelAndView(new RedirectView("/chd/login"));//입력값 없으면 로그인창으로
        }else{
            accessService.login(manager,session);
            if(session.getAttribute("sessionId")==null){
                return new ModelAndView(new RedirectView("/chd/login"));// 관리자 정보가 다르면 로그인창으로
            }
        }

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        accessService.logout(session);

        return new ModelAndView(new RedirectView("/chd/login"));
    }
}
