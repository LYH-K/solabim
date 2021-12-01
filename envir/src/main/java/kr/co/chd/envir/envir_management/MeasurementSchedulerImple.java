package kr.co.chd.envir.envir_management;

import kr.co.chd.envir.envir_device.MeasureCropEnvirUtil;
import kr.co.chd.envir.weather_info.SunTimeInfo;
import kr.co.chd.envir.weather_info.SunTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MeasurementSchedulerImple extends TimerTask implements MeasurementScheduler {
    private static LocalDateTime localDateTime;
    private static SunTimeUtil util = new SunTimeUtil();

    static {
        try {
            util.searchSunTime(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        MeasurementSchedulerImple measurementSchedulerImple = new MeasurementSchedulerImple();
        measurementSchedulerImple.getSunTimeInfo();
    }

    @Override
    public void getSunTimeInfo() throws Exception {
        Thread thread = new Thread(){
            @Override
            public void run() {
                MeasurementSchedulerImple measurementSchedulerImple = new MeasurementSchedulerImple();
                Timer timer = new Timer();
                timer.scheduleAtFixedRate(measurementSchedulerImple, 10000, 5000);
            }
        };

        thread.start();
    }

    //
    @Override
    public void run() {
        localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getMinute());
        LocalTime localTime = LocalTime.of(localDateTime.getHour(), localDateTime.getMinute());
        LocalTime midnight = LocalTime.of(0,10);
        LocalTime mid = LocalTime.of(0, 20);

        if(localTime.isAfter(midnight) && localTime.isBefore(mid)){
            try {
                util.searchSunTime(localDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Timer job = new Timer();
                if(util.resetSignal(localTime)){
                    CropEnvirServiceImple cropEnvirServiceImple = new CropEnvirServiceImple();
                    job.scheduleAtFixedRate(cropEnvirServiceImple, 600000,5400000);
                    Thread.sleep(2000);
                    job.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
