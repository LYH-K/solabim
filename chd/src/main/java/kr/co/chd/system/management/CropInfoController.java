package kr.co.chd.system.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
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
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList();
        String predictHarvest = cropAnalysisService.predictHarvest();

        modelAndView.addObject("list", cropAverageList);
        modelAndView.addObject("predictHarvest",predictHarvest);

        return modelAndView;
    }

    @GetMapping(value = "/list" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CropAverage> searchCropList(String date) {
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList(date);

        return cropAverageList;
    }

    @GetMapping(value = "/list/{no}" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CropAverage> searchCropList(@PathVariable int no) {
        List<CropAverage> cropAverageList = cropAnalysisService.searchCropList(no);

        return cropAverageList;
    }

    @GetMapping("/view")
    public ModelAndView searchView(String date){
        //질의 쿼리문으로 받아야지 날짜가 다 들어옴
        ModelAndView modelAndView = new ModelAndView("chd/view");
        List<CropInfo> cropInfoList = cropAnalysisService.searchAnalysisInfo(date);

        modelAndView.addObject("list",cropInfoList);

        return modelAndView;
    }

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
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("message", "OK");
        return msg;
    }

}
