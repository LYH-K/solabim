package kr.co.chd.facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map;

@RestController
=======
import java.io.IOException;

@Controller
>>>>>>> origin/master
@RequestMapping("/chd/analysis")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/control")
<<<<<<< HEAD
    public Map<String, String> receiveFacilityInfo(@RequestBody EnvirInfo envirInfo) throws InterruptedException {
=======
    public ModelAndView receiveFacilityInfo(@RequestBody EnvirInfo envirInfo) throws InterruptedException, IOException {
>>>>>>> origin/master
        ModelAndView modelAndView = new ModelAndView("test"); //라즈비안에서 수신했는지 확인하기 위함 추후 void로 변경해야함.
//        facilityService.controlFacility(envirInfo);
        System.out.println("don");
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("mssage", "OK");
        return msg;
    }
}
