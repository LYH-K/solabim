package kr.co.chd.envir.management;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalTime;

@Component
public class MeasurementSchedulerImp implements  MeasurementScheduler {
    private Logger logger = LogManager.getLogger(MeasurementSchedulerImp.class);
    private final long time = 150000;
    private static int num = 0;

    //1시간 30분마다 작동
    @Override
    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
    public void getTimeStart(){
        LocalTime localTime = LocalTime.now();

        System.out.println(num);

        if(num % 2 != 0){
            try {
                System.out.println("stop**************************************");
                resetSingnalWrite(true);
                Thread.sleep(time);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            System.out.println("start*****************************************");
            resetSingnalWrite(false);
        }
        num ++;
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
