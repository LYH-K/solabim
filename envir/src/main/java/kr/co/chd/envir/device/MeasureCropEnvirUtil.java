package kr.co.chd.envir.device;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;

import java.io.IOException;

public class MeasureCropEnvirUtil {
    private static float illuminance;
    private static final long time = 2000;

    public static void main(String[] args) {
        MeasureCropEnvirUtil measureCropEnvirUtil = new MeasureCropEnvirUtil();
        try {
            measureCropEnvirUtil.measure();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //측정
    public CropEnvirInfo measure() {
        final GpioController gpio = GpioFactory.getInstance();

        CropEnvirInfo cropEnvirInfoMeasure = new CropEnvirInfo();
        try {

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

            //가로축 각도 최대각도 찾기
            System.out.println("maxVerticalAngleStart");
            int maxVerticalAngle = maxVerticalAngle(verticalMotor);

            //최대 각도로 돌아가기
            System.out.println("maxvertical" + maxVerticalAngle);
            System.out.println("maxVerticalMotorStart");
            maxVerticalMotor(maxVerticalAngle, verticalMotor);

            //가로축 모터 30도
            horizontalMotor(horizontalMotor);

            //가로축 모터 최대 가로축 각도 및 조도세기 넣기
            int maxHorizontalAngle = maxHorizontalMotor(horizontalMotor);

            //세로축 모터 최대각도 넣기
            cropEnvirInfoMeasure.setVerticalAngle(maxVerticalAngle);
            cropEnvirInfoMeasure.setHorizontalAngle(maxHorizontalAngle);
            cropEnvirInfoMeasure.setIlluminance(illuminance);

            System.out.println("getVerticalAngle" + cropEnvirInfoMeasure.getVerticalAngle());
            System.out.println("getHorizontalAngle" + cropEnvirInfoMeasure.getHorizontalAngle());

            verticalReset(cropEnvirInfoMeasure.getVerticalAngle(), verticalMotor);
            horizontalReset(cropEnvirInfoMeasure.getHorizontalAngle(), horizontalMotor);

            if (cropEnvirInfoMeasure.getVerticalAngle() > 60 && cropEnvirInfoMeasure.getVerticalAngle() < 120) {
                cropEnvirInfoMeasure.setVerticalAngle(60);
                int resultHorizontal = cropEnvirInfoMeasure.getHorizontalAngle() + 180;

                if (resultHorizontal > 360) {
                    resultHorizontal -= 360;
                }
                cropEnvirInfoMeasure.setHorizontalAngle(resultHorizontal);
            }

            System.out.println("resultvertical" + cropEnvirInfoMeasure.getVerticalAngle());
            System.out.println("resulthorizontal" + cropEnvirInfoMeasure.getHorizontalAngle());
            System.out.println("resultillumant" + cropEnvirInfoMeasure.getIlluminance());

            verticalMotor.stop();
            horizontalMotor.stop();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            gpio.shutdown();
        }
        return cropEnvirInfoMeasure;
    }

    //세로축 최대값 구하기
    private int maxVerticalAngle(GpioStepperMotorComponent verticalMotor) throws Exception {
        CropEnvirInfo[] cropEnvirInfos = new CropEnvirInfo[5];
        for(int i = 0; i < 5; i++) {
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
            System.out.println("verticalilluminance" + illuminance);
            System.out.println("verticalAngle" + cropEnvirInfos[i].getVerticalAngle());
        }

        float max = cropEnvirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 0; i < 5; i++) {
            if(max < cropEnvirInfos[i].getIlluminance()) {
                max = cropEnvirInfos[i].getIlluminance();
                angle = cropEnvirInfos[i].getVerticalAngle();
            }
        }

        angle += 30;

        System.out.println("maxverticalilluminance " + max);
        System.out.println("maxverticalAngle " + angle);

        return angle;
    }

    //조도 측정
    private float illuminanceMeasurement(){
        IlluminanceUtil bh1750fvi = null;
        try {
            bh1750fvi = IlluminanceUtil.getInstance(I2CBus.BUS_1, IlluminanceUtil.I2C_ADDRESS_23);

            illuminance = bh1750fvi.getOptical();

            Thread.sleep(time);

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
        Thread.sleep(time);
    }

    //세로축 각도 최대 위치로
    private void maxVerticalMotor(int MaxAngle, GpioStepperMotorComponent verticalMotor){
        int angle = ((150 - MaxAngle) / 30) * 336;
        System.out.println("moveVertical" + angle);
        verticalMotor.step(angle);
    }

    //가로축 각도
    private void horizontalMotor(GpioStepperMotorComponent horizontalMotor) throws InterruptedException {
        horizontalMotor.step(-336);
        Thread.sleep(time);
    }

    //가로축 각도 최대각도
    private int maxHorizontalMotor(GpioStepperMotorComponent horizontalMotor) throws Exception {

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

            cropEnvirInfos[i].setHorizontalAngle((i+1)*30);
            cropEnvirInfos[i].setIlluminance(illuminance);
            System.out.println("horizontalilluminance" + illuminance);
            System.out.println("horizontalAngle" + cropEnvirInfos[i].getHorizontalAngle());
        }

        float max = cropEnvirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 0; i < 11; i++) {
            if(max < cropEnvirInfos[i].getIlluminance()) {
                max = cropEnvirInfos[i].getIlluminance();
                angle = cropEnvirInfos[i].getHorizontalAngle();
            }
        }

        illuminance = max;
        System.out.println("maxhorizontalilluminance " + max);
        System.out.println("maxhorizontal " + angle);

        return angle;
    }

    //세로축 각도 초기화
    private void verticalReset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = ((180 - maxAngle) / 30) * 336;
        System.out.println("moveVerticalReset" + angle);
        motor.step(angle);
    }

    //가로축 각도 초기화
    private void horizontalReset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = ((360 - maxAngle) / 30) * 336;
        System.out.println("moveHorizontalReset" + angle);
        motor.step(angle);
    }
}