package kr.co.chd.envir.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

public class WatchServiceDriver {
    private StringBuffer resetSignal = new StringBuffer();
    private CropEnvirService cropEnvirService = new CropEnvirService();

    public static void main(String[] args) {
        WatchServiceDriver watchServiceDriver = new WatchServiceDriver();

        System.out.println("start");

        watchServiceDriver.measureInfostartService();
    }

    // 1시간 30분 마다 디바이스 작동
    public void measureInfostartService() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get("/home/pi/Desktop/envirInfo");
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey watchKey = watchService.take();
                List<WatchEvent<?>> events = watchKey.pollEvents();

                for (WatchEvent<?> event : events) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path context = (Path) event.context();
                    measureInforeadFile();
                    boolean singal = Boolean.valueOf(resetSignal.toString());
                    System.out.println(singal);

                    if (!singal) {
                        cropEnvirService.measureCropEnvir(singal);
                    } else {
                        CropEnvirInfo cropEnvirInfo = new CropEnvirInfo();
                        cropEnvirInfo.setResetSignal(true);
                        cropEnvirService.sendCropEnvirInfo(cropEnvirInfo);
                    }
                }
                if (!watchKey.reset()) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1시간 30분마다 읽기
    public void measureInforeadFile() {
        File file = new File("/home/pi/Desktop/envirInfo/MeasureSend.txt");
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            resetSignal.replace(0, 1000, bufferedReader.readLine());// 가져온다.

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
