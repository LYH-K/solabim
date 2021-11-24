package kr.co.chd.facility;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/chd/analysis")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/control")
    public ModelAndView receiveFacilityInfo(@RequestBody EnvirInfo envirInfo) {
        ModelAndView modelAndView = new ModelAndView("test"); //라즈비안에서 수신했는지 확인하기 위함 추후 void로 변경해야함.
        facilityService.controlFacility(envirInfo);

        return modelAndView;
    }



}
