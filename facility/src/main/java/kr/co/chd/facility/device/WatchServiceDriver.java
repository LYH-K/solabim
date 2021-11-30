package kr.co.chd.facility.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.List;

public class WatchServiceDriver {
    private StringBuilder controlInfo = new StringBuilder();
    private MotorDriver motorMapper = new MotorDriver();

    public static void main(String args[]) {
        new WatchServiceDriver().startService();
    }

    public void startService(){
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get("/home/pi/Desktop/facilityControlInfo");
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey watchKey = watchService.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (WatchEvent<?> event : events) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path context = (Path) event.context();
                    readFile();// 수신한 정보를 읽고 controlInfo에 저장
                    motorMapper.controlFacility(controlInfo);
                }
                if (!watchKey.reset()) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFile(){
        File file = new File("/home/pi/Desktop/facilityControlInfo/control.txt");
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            controlInfo.replace(0,1000,bufferedReader.readLine());//수신한 각도와 초기화 신호 정보 읽어들이기

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}