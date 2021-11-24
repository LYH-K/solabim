package kr.co.facility.facility.service;

import kr.co.facility.facility.model.CropAnalysis;
import kr.co.facility.facility.model.EnvirInfo;

public interface FacilityService {
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo);
    public void controlFacility(EnvirInfo envirInfo);
    public CropAnalysis analysisCrop();
    public void sendCropInfo(CropAnalysis cropAnalysis);
}
