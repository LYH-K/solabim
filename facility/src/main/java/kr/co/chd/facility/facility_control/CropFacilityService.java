package kr.co.chd.facility.facility_control;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CropFacilityService {
    public void updateCropFacilityInfo(CropEnvirInfo cropEnvirInfo);
}
