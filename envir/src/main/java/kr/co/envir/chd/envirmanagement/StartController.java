package kr.co.envir.chd.envirmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class StartController {
    @Autowired
    private EnvirService envirService;

    public static void main(String[] args) throws Exception {

        EnvirService envirService = new EnvirServiceImple();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    envirService.now();
                    envirService.measureEnvir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
