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
    @Autowired
    DataMapper dataMapper;

    @Override
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo) {
        return null;
    }

    @Override
    public void controlFacility(EnvirInfo envirInfo) throws InterruptedException, IOException {
        Thread thread = new Thread(new MotorMapper());
        thread.start();//농작물 위치 변경 기능
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

    @Override
    public void sendCropInfo(CropAnalysis cropAnalysis) {
        try {
            final MediaType MULTIPART = MediaType.parse("multipart/form-data");
            final String requestUrl = "http://localhost:80/chd/analysis";

            //파일의 위치는 수정이 필요함함
            File cropSideImage = new File("C:\\Users\\sdm05\\Desktop\\cropInfo\\cropSideImage.jpg");
            File cropVerticalImage = new File("C:\\Users\\sdm05\\Desktop\\cropInfo\\cropVerticalImage.jpg");
            String growth = "50";

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new MultipartBody.Builder().
                    setType(MultipartBody.FORM).
                    addFormDataPart("growth", String.valueOf(growth)).
                    addFormDataPart("cropSideImage", cropSideImage.getName(), RequestBody.create(MULTIPART, cropSideImage)).
                    addFormDataPart("cropVerticalImage", cropVerticalImage.getName(), RequestBody.create(MULTIPART, cropVerticalImage))
                    .build();

            Request request = new Request.Builder().
                    url(requestUrl).
                    post(requestBody).
                    build();

            Response response = client.newCall(request).execute();

        } catch (Exception e) {
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
        try (InputStream psout = process.getInputStream()) {
            readPythonResult(psout, System.out);
        }
    }

    private void readPythonResult(InputStream input, OutputStream output) throws IOException {
        try {
            String steamToString = IOUtils.toString(input);

            String[] rgbs = steamToString.split("'");
            String[] pythonHorizonRGB = rgbs[1].split(",");
            String[] pythonVerticalRGB = rgbs[3].split(",");

            List<Integer> horizonRGB = new ArrayList<Integer>();

            for (int i = 0; i < pythonHorizonRGB.length; i++){
                horizonRGB.add(Integer.parseInt(pythonHorizonRGB[i]));
            }

            List<Integer> verticalRGB = new ArrayList<Integer>();

            for (int i = 0; i < pythonVerticalRGB.length; i++){
                verticalRGB.add(Integer.parseInt(pythonVerticalRGB[i]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int search(List<Integer> horizonRGB, List<Integer> verticalRGB) {
        dataMapper.selectAll();
        return 0;
    }
}
