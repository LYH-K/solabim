package kr.co.chd.facility;

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
