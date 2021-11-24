package kr.co.facility.facility.presentation;

import kr.co.facility.facility.model.EnvirInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chd/analysis")
public class FacilityController {
    @PostMapping("/control")
    public void receiveFacilityInfo(EnvirInfo envirInfo) {
    }
}
