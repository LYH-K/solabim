package kr.co.chd.facility.facility_control;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CropFacilityServceImp implements CropFacilityService{
    @Override
    public void updateCropFacilityInfo(CropEnvirInfo cropEnvirInfo){
        final String filePath = "/home/pi/Desktop/facilityControlInfo/control.txt";
        BufferedWriter bufferedWriter = null;
        File file = new File(filePath);
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write((cropEnvirInfo.getHorizontalAngle()+"/"+ cropEnvirInfo.getVerticalAngle()+"/"+cropEnvirInfo.isResetSignal()));
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
