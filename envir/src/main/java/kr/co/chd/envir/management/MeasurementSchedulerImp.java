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
    private final long time = 3600000;

    //1시간마다 작동
    @Override
    @Scheduled(fixedRate = time, initialDelay = 1000)
    public void getTimeStart(){
        LocalTime localTime = LocalTime.now();
        LocalTime startTime = LocalTime.of(7, 0 , 0);
        LocalTime stopTime = LocalTime.of(18, 0, 0);
        boolean resetSignal = localTime.isAfter(stopTime);
        boolean signal = !(localTime.isBefore(stopTime) && localTime.isAfter(startTime));

        if(resetSignal){
            System.out.println(signal);

            if(signal){
                try {
                    logger.debug("stop**************************************");
//                    resetSingnalWrite(resetSignal);
                    Thread.sleep(time);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } else {

                System.out.println("start");
                logger.debug("start*****************************************");
                resetSingnalWrite(resetSignal);
            }
    }
}

    //
    private void resetSingnalWrite(boolean resetSignal) {
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
