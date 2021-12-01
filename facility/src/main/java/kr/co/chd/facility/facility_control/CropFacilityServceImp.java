package kr.co.chd.facility.facility_control;

import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CropFacilityServceImp implements CropFacilityService{
    @Override
    public void saveCrioFacilityInfo(CropEnvirInfo cropEnvirInfo){
        final String filePath = "/home/pi/Desktop/facilityControlInfo/control.txt";
        BufferedWriter bufferedWriter = null;
        File file = new File(filePath);
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write(String.valueOf(cropEnvirInfo.getHorizontalAngle()+"/"+ cropEnvirInfo.getVerticalAngle()+"/"+cropEnvirInfo.isResetSignal()));
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
