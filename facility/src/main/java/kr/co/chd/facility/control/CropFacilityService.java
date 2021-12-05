package kr.co.chd.facility.control;

import org.springframework.stereotype.Service;

@Service
public interface CropFacilityService {
    public void updateCropFacilityInfo(CropEnvirInfo cropEnvirInfo);
}
