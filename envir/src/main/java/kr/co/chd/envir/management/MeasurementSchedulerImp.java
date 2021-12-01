package kr.co.chd.envir.management;

import kr.co.chd.envir.weatherinfo.SunTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class MeasurementSchedulerImp extends TimerTask implements MeasurementScheduler {
    private static LocalDateTime localDateTime;
    private static SunTimeUtil util = new SunTimeUtil();

    static {
        try {
            util.searchSunTime(LocalDate.now());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSunTimeInfo() throws Exception {
        Thread thread = new Thread(){
            @Override
            public void run() {
                MeasurementSchedulerImp measurementSchedulerImple = new MeasurementSchedulerImp();
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
                    CropEnvirServiceImp cropEnvirServiceImple = new CropEnvirServiceImp();
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
