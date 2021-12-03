package kr.co.chd.envir.envir_management;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Start {

    @Scheduled(cron = "*/1 * * * * *")
    public void start(){
        System.out.println("start");
    }
}
