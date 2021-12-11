package kr.co.chd.system.management;

import jakarta.servlet.http.HttpSession;
import kr.co.chd.system.page.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chd")
public class CropInfoController {
    @Autowired
    private CropAnalysisService cropAnalysisService;
    @Autowired
    private CropFacilityService cropFacilityService;
    @Autowired
    private PageUtil pageUtil;

    @PostMapping("/analysis")//농작물 측면, 수직, 생장률 받는다.
    public Map<String,String> receiveAnalysisInfo(CropAnalysis cropAnalysis){
        cropAnalysis = cropAnalysisService.analysisCrop(cropAnalysis);
        cropAnalysisService.addCropAnalysis(cropAnalysis);

        Map<String,String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");

        return msg;
    }

    @GetMapping("/list")
    public ModelAndView searchCropList(){
        ModelAndView modelAndView = new ModelAndView("chd/list");
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList(new HashMap<>());
        String predictHarvest = cropAnalysisService.predictHarvest();
        int totalData = cropAnalysisService.countBoard();


        modelAndView.addObject("list", cropAverageList);
        modelAndView.addObject("predictHarvest",predictHarvest);
        modelAndView.addObject("total",totalData);
        modelAndView.addObject("top", cropAnalysisService.searchCrops());

        return modelAndView;
    }

    @PostMapping(value = "/list")
    public Map<String, Object> searchCropList(@RequestBody Map<String, String> condition) {
        Map<String, String> allCondtion = new HashMap<>();

        allCondtion.put("date", condition.get("date"));
        int allNo = cropAnalysisService.searchCropList(allCondtion).size();

        int pageNo = Integer.parseInt(condition.get("pageNo")) * 10;

        allCondtion.put("pageNo", String.valueOf(pageNo));

        Map<String, Object> result = new HashMap<String, Object>();
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList(allCondtion);

        result.put("cropAverages", cropAverageList);

        String navigator = pageUtil.getNavigator(allNo, Integer.parseInt(condition.get("pageNo")));

        result.put("navigator", navigator);

        return result;
    }

    @GetMapping(value = "/list/{no}" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CropAverage> searchCropList(@PathVariable int no) {
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList(no);

        return cropAverageList;
    }

    //날짜에 따른 시간 데이터 검색
    @GetMapping("/view")
    public ModelAndView searchView(String date){
        //질의 쿼리문으로 받아야지 날짜가 다 들어옴
        ModelAndView modelAndView = new ModelAndView("chd/view");
        List<CropInfo> cropInfoList = cropAnalysisService.searchAnalysisInfo(date);
        CropAverage dateAverage = cropAnalysisService.searchDateAverage(date);

        modelAndView.addObject("list",cropInfoList);
        modelAndView.addObject("dateAverage", dateAverage);

        return modelAndView;
    }

    //농작물 이미지 검색
    @GetMapping(value = "/view/{no}" ,produces= MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] searchImage(@PathVariable int no){
        CropAnalysis cropAnalysis = cropAnalysisService.searchImage(no);
        File cropSideImage = new File(cropAnalysis.getCropSideImageURL());
        FileInputStream fileInputStream = null;

        int count = 0;
        byte cropImage[] = new byte[Integer.parseInt(String.valueOf(cropSideImage.length()))];

        try{
            fileInputStream = new FileInputStream(cropSideImage);

            while((count = fileInputStream.read(cropImage)) != -1){
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return cropImage;
    }

    @PostMapping("/envir") //농작물 환경 정보 수신
    public Map<String, String> reciveEnvirInfo(@RequestBody CropEnvirInfo cropEnvirInfo) throws InterruptedException {
        try{
            cropAnalysisService.addCropEnvirInfo(cropEnvirInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        cropFacilityService.sendCropEnvirInfo(cropEnvirInfo);
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");
        return msg;
    }
}
