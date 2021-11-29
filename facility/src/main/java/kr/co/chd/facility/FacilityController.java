package kr.co.chd.facility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chd/analysis")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping("/control")
    public Map<String, String> receiveFacilityInfo(@RequestBody EnvirInfo envirInfo) throws InterruptedException, IOException {
        System.out.println(envirInfo.isResetSignal());

        facilityService.controlFacility(envirInfo);

        Map<String, String> msg = new HashMap<String, String>();
        msg.put("code", "200");
        msg.put("mssage", "OK");
        return msg;
    }
}
