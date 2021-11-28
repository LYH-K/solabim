package kr.co.chd.facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chd/analysis")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/control")
    public Map<String, String> receiveFacilityInfo(EnvirInfo envirInfo) throws InterruptedException, IOException {
        facilityService.controlFacility(envirInfo);

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
