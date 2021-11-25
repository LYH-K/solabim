package kr.co.chd.facility;

import org.springframework.stereotype.Service;

@Service
public interface FacilityService {
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo);
    public void controlFacility(EnvirInfo envirInfo) throws InterruptedException;
    public CropAnalysis analysisCrop();
    public void sendCropInfo(CropAnalysis cropAnalysis);
}
