package kr.co.chd.envir.device;

import kr.co.chd.envir.management.CropEnvirServiceImp;
import kr.co.chd.envir.weatherinfo.SunTimeInfo;
import kr.co.chd.envir.weatherinfo.SunTimeUtil;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

@Component
public class WatchServiceDriver {
    private StringBuilder nowDate = new StringBuilder();
    private StringBuilder resetSignal = new StringBuilder();
    private static SunTimeUtil sunTimeUtil = new SunTimeUtil();
    private CropEnvirServiceImp cropEnvirServiceImp = new CropEnvirServiceImp();
    private SunTimeInfo sunTimeInfo = new SunTimeInfo();

    static {
        try {
            sunTimeUtil.searchSunTime(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //1시간 30분 마다 디바이스 작동
    public void MeasureInfostartService(){
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path path = Paths.get("/home/pi/Desktop/envirIfo");
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
                    MeasureInforeadFile();
                    boolean singal = Boolean.valueOf(resetSignal.toString());
                    if(singal){
                        cropEnvirServiceImp.measureCropEnvir();
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

    //1시간 30분마다 읽기
    public void MeasureInforeadFile(){
        File file = new File("/home/pi/Desktop/envirIfo/MeasureSend.txt");
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            resetSignal.replace(0,1000,bufferedReader.readLine());// 가져온다.

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
