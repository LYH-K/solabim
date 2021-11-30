package kr.co.chd.envir.envir_management;

import org.springframework.beans.factory.annotation.Autowired;

public class StartController {
    @Autowired
    private CropEnvirService cropEnvirService;

    public static void main(String[] args) throws Exception {

        CropEnvirService cropEnvirService = new CropEnvirServiceImple();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    cropEnvirService.now();
                    cropEnvirService.measureCropEnvir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
