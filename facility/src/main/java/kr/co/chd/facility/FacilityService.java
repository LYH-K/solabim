package kr.co.chd.facility;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FacilityService {
    public void controlFacility(EnvirInfo envirInfo) throws InterruptedException, IOException;
    public void sendCropInfo(CropAnalysis cropAnalysis);
}
