package kr.co.chd.facility;

import org.springframework.stereotype.Service;

@Service
public class FacilityServceImp implements FacilityService{

    @Override
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo) {
        return null;
    }

    @Override
    public void controlFacility(EnvirInfo envirInfo) {
        int verticalAngle = envirInfo.getVerticalAngle();//세로축 각도
        int horizontalAngle = envirInfo.getVerticalAngle();

        System.out.println("세로축 : " + verticalAngle +"\n 가로축"+ horizontalAngle);
    }

    @Override
    public CropAnalysis analysisCrop() {
        return null;
    }

    @Override
    public void sendCropInfo(CropAnalysis cropAnalysis) {

    }
}
