package kr.co.chd.facility.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chd/analysis")
public class CropFacilityController {
    @Autowired
    private CropFacilityService cropFacilityService;

    @PostMapping("/control")
    public Map<String, String> receiveFacilityInfo(@RequestBody CropEnvirInfo cropEnvirInfo) {
        cropFacilityService.updateCropFacilityInfo(cropEnvirInfo);

        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("mssage", "OK");
        return msg;
    }
}
