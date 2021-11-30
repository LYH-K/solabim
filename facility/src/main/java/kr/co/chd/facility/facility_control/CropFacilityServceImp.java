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
    @Autowired
    DataMapper dataMapper;
    @Autowired
    CropAnalysis cropAnalysis;

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

    @Override
    public void analysisCrop() {
        String[] command = new String[2];
        command[0] = "python";
        command[1] = "C:\\Users\\djaak\\Desktop\\computervision\\project\\camera.py";

        try {
            byProcessBuilder(command);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void byProcessBuilder(String[] command)
            throws IOException,InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        printStream(process);
    }

    private void printStream(Process process)
            throws IOException, InterruptedException {
        process.waitFor();
        try (InputStream pythonResult = process.getInputStream()) {
            readPythonResult(pythonResult, System.out);
        }
    }

    private CropAnalysis readPythonResult(InputStream input, OutputStream output) throws IOException {
        try {
            CropData analysisCropData = dataMapper.selectAll();

            String steamToString = IOUtils.toString(input);

            String[] rgbs = steamToString.split("'");
            String[] pythonSideRGB = rgbs[5].split(",");
            String[] pythonVerticalRGB = rgbs[7].split(",");

            List<Float> sideRGB = new ArrayList<Float>();

            for (int i = 0; i < pythonSideRGB.length; i++) {
                sideRGB.add(Float.parseFloat(pythonSideRGB[i]));
            }

            List<Float> verticalRGB = new ArrayList<Float>();

            for (int i = 0; i < pythonVerticalRGB.length; i++) {
                verticalRGB.add(Float.parseFloat(pythonVerticalRGB[i]));
            }

            int sideR = (int)(sideRGB.get(0) / analysisCropData.getCropSideR());
            int sideG = (int)(sideRGB.get(1) / analysisCropData.getCropSideG());
            int sideB = (int)(sideRGB.get(2) / analysisCropData.getCropSideB());
            int sideGrowth = (int)Math.round(((sideR * 0.299) + (sideG * 0.587) + (sideB * 0.114)) * 100);

            int verticalR = (int)(verticalRGB.get(0) / analysisCropData.getCropVerticalR());
            int verticalG = (int)(verticalRGB.get(1) / analysisCropData.getCropVerticalG());
            int verticalB = (int)(verticalRGB.get(2) / analysisCropData.getCropVerticalB());
            int verticalGrowth = (int)Math.round(((verticalR * 0.299) + (verticalG * 0.587) + (verticalB * 0.114)) * 100);

            int growth = ((sideGrowth + verticalGrowth) / 2);

            cropAnalysis.setCropSideImageURL(rgbs[1]);
            cropAnalysis.setCropVerticalImageURL(rgbs[3]);
            cropAnalysis.setGrowth(growth);

            System.out.print(cropAnalysis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cropAnalysis;
    }
}
