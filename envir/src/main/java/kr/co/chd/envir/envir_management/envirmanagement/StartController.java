package kr.co.chd.envir.envir_management.envirmanagement;

import org.springframework.beans.factory.annotation.Autowired;

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
