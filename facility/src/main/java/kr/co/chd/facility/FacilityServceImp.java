package kr.co.chd.facility;

import kr.co.chd.facility.device.MotorMapper;
import org.springframework.stereotype.Service;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        int horizontalAngle = envirInfo.getHorizonAngle();

        System.out.println("start motorController");

        thread.start();
    }

    @Override
    public void analysisCrop() {
        String[] command = new String[2];
        command[0] = "python";
        command[1] = "C:\\Users\\djaak\\Desktop\\computervision\\project\\camera.py";

        try {
            executePython(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCropInfo(CropAnalysis cropAnalysis) {

    }

    //파이썬 코드 호출(영상 분석 파일 저장 기능)
    public void executePython(String[] command)
            throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        System.out.println("output: " + outputStream.toString());
    }
}
