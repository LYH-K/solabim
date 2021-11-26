package kr.co.chd.facility;

import kr.co.chd.facility.device.MotorMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FacilityServceImp implements FacilityService{

    @Override
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo) {
        return null;
    }

    @Override
    public void controlFacility(EnvirInfo envirInfo) throws InterruptedException, IOException {
        Thread thread = new Thread(new MotorMapper());

        int verticalAngle = envirInfo.getVerticalAngle();//세로축 각도
        int horizontalAngle = envirInfo.getVerticalAngle();

        System.out.println("start motorController");

        thread.start();
    }

    @Override
    public CropAnalysis analysisCrop() {
        return null;
    }

    @Override
    public void sendCropInfo(CropAnalysis cropAnalysis) {

    }
}
