package kr.co.chd.system.analysis_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chd")
public class CropInfoController {
    @Autowired
    private CropAnalysisService cropAnalysisService;
    @Autowired
    private CropEnvirMapper cropEnvirMapper;
    @Autowired
    private CropFacilityService cropFacilityService;

    @PostMapping("/analysis")//농작물 측면, 수직, 생장률 받는다.
    public Map<String,String> receiveAnalysisInfo(CropAnalysis cropAnalysis){
        cropAnalysisService.addCropAnalysis(cropAnalysis);

        Map<String,String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");

        return msg;
    }

    @GetMapping("/list")
    public ModelAndView searchCropList(){
        ModelAndView modelAndView = new ModelAndView("chd/list");
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList();
        String predictHarvest = cropAnalysisService.predictHarvest();

        modelAndView.addObject("list", cropAverageList);
        modelAndView.addObject("predictHarvest",predictHarvest);

        return modelAndView;
    }

    @GetMapping(value = "/list" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CropAverage> searchCropList(String date) {
        System.out.println(date);
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList(date);

        return cropAverageList;
    }

    @GetMapping("/view")
    public ModelAndView searchView(String date){
        //질의 쿼리문으로 받아야지 날짜가 다 들어옴
        System.out.println(date);
        ModelAndView modelAndView = new ModelAndView("chd/view");
        List<CropInfo> cropInfoList = cropAnalysisService.searchAnalysisInfo(date);

        modelAndView.addObject("list",cropInfoList);

        return modelAndView;
    }

    @PostMapping("/envir") //농작물 환경 정보 수신
    public Map<String, String> reciveEnvirInfo(CropEnvirInfo cropEnvirInfo) throws InterruptedException {
        cropEnvirMapper.insert(cropEnvirInfo);
        cropFacilityService.sendCropAnalysisInfo(cropEnvirInfo);
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");
        return msg;
    }

}
