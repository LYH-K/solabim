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

//디바이스를 실행 시키기 위한 프로그램
public class WatchServiceDriver {
    private StringBuffer resetSignal = new StringBuffer();
    private CropEnvirServiceImp cropEnvirService = new CropEnvirServiceImp();

    public static void main(String[] args) {
        WatchServiceDriver watchServiceDriver = new WatchServiceDriver();

        System.out.println("start");

        watchServiceDriver.measureInfostartService();
    }

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
                        CropEnvirInfo cropEnvirInfo = cropEnvirService.measureCropEnvir(singal);
                        cropEnvirService.sendCropEnvirInfo(cropEnvirInfo);
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
