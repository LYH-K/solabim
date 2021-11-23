package system.co.kr.chd.Pressentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import system.co.kr.chd.model.Manager;

@Controller
@RequestMapping("/chd")
public class SystemController {

    @GetMapping("/login")
    public ModelAndView loginform(){
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView login(Manager manager){
        return new ModelAndView();
    }

    @GetMapping("/logout")
    public ModelAndView logout(){
        return new ModelAndView();
    }

    @PostMapping("/chd/envir")
    public ModelAndView receiveEnvirInfo(){
        return new ModelAndView();
    }

    @PostMapping("/chd/analysis")
    public ModelAndView receiveAnalysisInfo(){
        return new ModelAndView();
    }

    @GetMapping("/chd/list")
    public ModelAndView searchCropList(){
        return new ModelAndView();
    }

    @GetMapping("/chd/list/{date}")
    public ModelAndView searchCropList(String date){
        return new ModelAndView();
    }

    @GetMapping("/chd/list/{no}")
    public ModelAndView searchCropList(int no){
        return new ModelAndView();
    }

    @GetMapping("/chd/view/{date}")
    public ModelAndView searchView(){
        return new ModelAndView();
    }

    @GetMapping("/chd/view/{no}")
    public ModelAndView searchViewJson(){
        return new ModelAndView();
    }

}
