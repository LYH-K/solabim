package kr.co.chd.facility;

public interface FacilityService {
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo);
    public void controlFacility(EnvirInfo envirInfo);
    public CropAnalysis analysisCrop();
    public void sendCropInfo(CropAnalysis cropAnalysis);
}
