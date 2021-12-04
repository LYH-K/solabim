package kr.co.chd.envir.management;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalTime;

@Component
public class MeasurementSchedulerImp implements  MeasurementScheduler {
    private final long time = 600000;
    //private final long time = 5400000;
//    private Logger logger = (Logger) LogManager.getLogger(MeasurementSchedulerImp.class);

    //1시간 30분마다 작동
    @Override
    @Scheduled(fixedRate = time)
    public void getTimeStart(){
        LocalTime localTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(7, 0 , 0);
        LocalTime stopTime = LocalTime.of(19, 0, 0);
        boolean resetSignal = localTime.isAfter(stopTime);

        if(resetSignal){
            try {
                Thread.sleep(time);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            resetSingnalWrite(resetSignal);
            System.out.println("start");
        }
    }

    public void resetSingnalWrite(boolean resetSignal) {
        BufferedWriter bufferedWriter = null;

        try{
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    "/home/pi/Desktop/envirInfo/MeasureSend.txt"
                            )));

            bufferedWriter.write(String.valueOf(resetSignal));
            bufferedWriter.flush();
        } catch (IOException e){
            if(bufferedWriter != null){
                try{
                    bufferedWriter.close();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
