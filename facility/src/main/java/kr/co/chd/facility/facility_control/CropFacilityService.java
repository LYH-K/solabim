package kr.co.chd.facility.facility_control;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CropFacilityService {
    public CropEnvirInfo receiveCropFacilityInfo(CropEnvirInfo cropEnvirInfo);
    public void controlCropFacility(CropEnvirInfo cropEnvirInfo) throws InterruptedException, IOException;
    public void analysisCrop();
    public void sendCropInfo(CropAnalysis cropAnalysis);
}
