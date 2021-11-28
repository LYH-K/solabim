package kr.co.chd.system.analysis_management;

import org.apache.ibatis.javassist.bytecode.analysis.MultiType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping(value = "/list" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CropAverage> searchCropList(String date) {
        System.out.println(date);
        List<CropAverage> cropAverageList = analysisService.searchCrop(date);

        return cropAverageList;
    }

    @GetMapping("/view")
    public ModelAndView searchView(String date){
        //질의 쿼리문으로 받아야지 날짜가 다 들어옴
        System.out.println(date);
        ModelAndView modelAndView = new ModelAndView("chd/view");
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
