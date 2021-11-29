package kr.co.chd.system.analysis_management;

import jakarta.annotation.Resource;
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
    @Autowired
    private EnvirService envirService;

    @PostMapping("/analysis")//농작물 측면, 수직, 생장률 받는다.
    public Map<String,String> receiveAnalysisInfo(CropAnalysis cropAnalysis){
        analysisService.addCropAnalysis(cropAnalysis);

        Map<String,String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");

        return msg;
    }

    @GetMapping("/list")
    public ModelAndView searchCropList(){
        ModelAndView modelAndView = new ModelAndView("chd/list");
        List<CropAverage> cropAverageList = analysisService.searchCropList();
        String predictHarvest = analysisService.predictHarvest();

        modelAndView.addObject("list", cropAverageList);
        modelAndView.addObject("predictHarvest",predictHarvest);

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

    @PostMapping("/envir") //농작물 환경 정보 수신
    public Map<String, String> reciveEnvirInfo(EnvirInfo envirInfo) throws InterruptedException {
        envirService.receiveEnvirInfo(envirInfo);
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");
        return msg;
    }

}
