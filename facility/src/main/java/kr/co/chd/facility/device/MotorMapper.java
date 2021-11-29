package kr.co.chd.facility.device;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import kr.co.chd.facility.CropAnalysis;
import kr.co.chd.facility.EnvirInfo;
import kr.co.chd.facility.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.security.Provider;

public class MotorMapper implements Runnable {
    private EnvirInfo envirInfo;
    private String filePath = "/home/pi/Desktop/workspace/control.txt";
    private BufferedWriter bufferedWriter;

    public MotorMapper(EnvirInfo envirInfo){
        this.envirInfo = envirInfo;
    }

    @Override
    public void run() {
        File file = new File(filePath);
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write(String.valueOf(envirInfo.getHorizontalAngle()+"/"+ envirInfo.getVerticalAngle()+"/"+envirInfo.isResetSignal()));
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
