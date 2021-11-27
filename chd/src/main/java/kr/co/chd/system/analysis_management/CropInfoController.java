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
    @Autowired
    private AnalysisService analysisService;

    @PostMapping("/analysis")//농작물 측면, 수직, 생장률 받는다.
    public Map<String,String> receiveAnalysisInfo(MultiType multiType){
        Map<String,String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");

        return msg;
    }

    @GetMapping("/list")
    public ModelAndView searchCropList(){
        ModelAndView modelAndView = new ModelAndView("chd/list");
        List<CropAverage> cropAverageList = analysisService.searchCropList();

        modelAndView.addObject("list", cropAverageList);

        return modelAndView;
    }

    @GetMapping("/view/{date}")
    public ModelAndView searchView(@PathVariable(value = "date") String date){
        ModelAndView modelAndView = new ModelAndView("chd/time");
        List<CropInfo> cropInfoList = analysisService.searchAnalysisInfo(date);

        modelAndView.addObject("list",cropInfoList);
        return modelAndView;
    }


    @GetMapping("/envir")
    public ModelAndView reciveEnvirInfo(){
        ModelAndView mav = new ModelAndView("don");
        //mav.addObject("envirInfo", envirInfo);
        return mav;
    }

}
