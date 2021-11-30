package kr.co.chd.envir.envir_device;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;
import kr.co.chd.envir.envir_management.CropEnvirInfo;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MeasureCropEnvirUtil {
    static float illuminance;
    public static void main(String[] args) throws Exception {
        MeasureCropEnvirUtil measureEnvirUtil = new MeasureCropEnvirUtil();
        System.out.println("result : " + measureEnvirUtil.measure().getIlluminance());
    }
    
    //측정
    public CropEnvirInfo measure() throws Exception {
        CropEnvirInfo cropEnvirInfo = new CropEnvirInfo();

        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput[] verticalpins = {
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};

        final GpioPinDigitalOutput[] horizontalpins = {
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, PinState.LOW)};

        gpio.setShutdownOptions(true, PinState.LOW, verticalpins);
        gpio.setShutdownOptions(true, PinState.LOW, horizontalpins);

        GpioStepperMotorComponent verticalMotor = new GpioStepperMotorComponent(verticalpins);
        GpioStepperMotorComponent horizontalMotor = new GpioStepperMotorComponent(horizontalpins);

        byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;

        verticalMotor.setStepInterval(2);
        verticalMotor.setStepSequence(single_step_sequence);
        verticalMotor.setStepsPerRevolution(2038);

        horizontalMotor.setStepInterval(2);
        horizontalMotor.setStepSequence(single_step_sequence);
        horizontalMotor.setStepsPerRevolution(2038);

        int verticalAngle = maxVerticalAngle(verticalMotor);

        System.out.println("verticalMotor");
        //세로축 모터 30도
        verticalMotor(verticalMotor);

        System.out.println("maxVerticalAngle");
        maxVerticalAngle(verticalMotor);

        //최대 각도로 돌아가기
        System.out.println("maxVerticalMotor");
        maxVerticalMotor(cropEnvirInfo.getVerticalAngle(), verticalMotor);

        //가로축 모터 30도
        horizontalMotor(horizontalMotor);

         cropEnvirInfo = maxHorizontalMotor(horizontalMotor);
         cropEnvirInfo.setVerticalAngle(verticalAngle);

        verticalReset(cropEnvirInfo.getVerticalAngle(),verticalMotor);
        horizontalReset(cropEnvirInfo.getHorizontalAngle(), horizontalMotor);

        verticalMotor.stop();
        horizontalMotor.stop();

        gpio.shutdown();

        return cropEnvirInfo;
    }

    //세로축 최대값 구하기
    private int maxVerticalAngle(GpioStepperMotorComponent verticalMotor) throws Exception {
        CropEnvirInfo[] cropEnvirInfos = new CropEnvirInfo[4];
        for(int i = 0; i < 4; i++) {
            cropEnvirInfos[i] = new CropEnvirInfo();
            verticalMotor(verticalMotor);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    illuminance = illuminanceMeasurement();
                }
            };

            thread.run();
            thread.join();

            cropEnvirInfos[i].setVerticalAngle((i+1)*30);
            cropEnvirInfos[i].setIlluminance(illuminance);
        }

        float max = cropEnvirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 0; i < 4; i++) {
            if(max < cropEnvirInfos[i].getIlluminance()) {
                max = cropEnvirInfos[i].getIlluminance();
                angle = cropEnvirInfos[i].getVerticalAngle();
            }
        }

        if(angle > 60) {
            angle = 60;
        }

        return angle;
    }

    //조도 측정
    private float illuminanceMeasurement(){
        IlluminanceUtil bh1750fvi = null;
        try {
            bh1750fvi = IlluminanceUtil.getInstance(I2CBus.BUS_1, IlluminanceUtil.I2C_ADDRESS_23);

            illuminance = bh1750fvi.getOptical();

            Thread.sleep(10000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bh1750fvi != null) {
                try {
                    bh1750fvi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return illuminance;
    }

    //세로축 각도
    private void verticalMotor(GpioStepperMotorComponent verticalMotor) throws InterruptedException {
        verticalMotor.step(-336);
        Thread.sleep(10000);
    }

    //세로축 각도 최대 위치로
    private void maxVerticalMotor(int MaxAngle, GpioStepperMotorComponent verticalMotor){
        int angle = (180 - MaxAngle) / 30 * 336;
        verticalMotor.step(angle);
    }

    //가로축 각도
    private void horizontalMotor(GpioStepperMotorComponent horizontalMotor) throws InterruptedException {
        horizontalMotor.step(-336);
        Thread.sleep(10000);
    }

    //가로축 각도 최대각도
    private CropEnvirInfo maxHorizontalMotor(GpioStepperMotorComponent horizontalMotor) throws Exception {

        CropEnvirInfo[] cropEnvirInfos = new CropEnvirInfo[11];

        for(int i = 0; i < 11; i++) {
            cropEnvirInfos[i] = new CropEnvirInfo();
            horizontalMotor(horizontalMotor);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    illuminance = illuminanceMeasurement();
                }
            };

            thread.run();
            thread.join();

            cropEnvirInfos[i].setVerticalAngle((i+1)*30);
            cropEnvirInfos[i].setIlluminance(illuminance);
        }

        float max = cropEnvirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 0; i < 11; i++) {
            if(max < cropEnvirInfos[i].getIlluminance()) {
                max = cropEnvirInfos[i].getIlluminance();
                angle = cropEnvirInfos[i].getVerticalAngle();
            }
        }

        if(angle > 60) {
            angle = 60;
        }

        CropEnvirInfo cropEnvirInfo = new CropEnvirInfo();
        cropEnvirInfo.setIlluminance(max);
        cropEnvirInfo.setVerticalAngle(angle);

        return cropEnvirInfo;
    }

    //세로축 각도 초기화
    private void verticalReset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = (180 - maxAngle) / 30 * 336;
        motor.step(angle);
    }

    //가로축 각도 초기화
    private void horizontalReset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = (360 - maxAngle) / 30 * 336;
        motor.step(angle);
    }
}