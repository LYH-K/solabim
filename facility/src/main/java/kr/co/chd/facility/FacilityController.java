package kr.co.chd.facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chd/analysis")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/control")
    public Map<String, String> receiveFacilityInfo(@RequestBody EnvirInfo envirInfo) throws InterruptedException {
        ModelAndView modelAndView = new ModelAndView("test"); //라즈비안에서 수신했는지 확인하기 위함 추후 void로 변경해야함.
//      facilityService.controlFacility(envirInfo);
        System.out.println("don");
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("mssage", "OK");
        return msg;
    }

    @GetMapping("/tt")
    public String te() {
        facilityService.analysisCrop();
        return "/jsp/test.jsp";
    }

    @GetMapping("/test")
    public String test() {
        facilityService.analysisCrop();
        return "test";
    }
}
