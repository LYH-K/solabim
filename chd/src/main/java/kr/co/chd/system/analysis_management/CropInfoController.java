package kr.co.chd.system.analysis_management;

import org.apache.ibatis.javassist.bytecode.analysis.MultiType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chd")
public class CropInfoController {
    @PostMapping("/analysis")//농작물 측면, 수직, 생장률 받는다.
    public Map<String,String> receiveAnalysisInfo(MultiType multiType){
        Map<String,String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");

        return msg;
    }

    @GetMapping("/envir")
    public ModelAndView reciveEnvirInfo(){
        ModelAndView mav = new ModelAndView("don");
        //mav.addObject("envirInfo", envirInfo);
        return mav;
    }
}
