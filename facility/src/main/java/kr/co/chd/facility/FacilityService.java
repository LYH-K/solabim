package kr.co.chd.facility;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FacilityService {
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo);
    public void controlFacility(EnvirInfo envirInfo) throws InterruptedException, IOException;
    public CropAnalysis analysisCrop();
    public void sendCropInfo(CropAnalysis cropAnalysis);
}
