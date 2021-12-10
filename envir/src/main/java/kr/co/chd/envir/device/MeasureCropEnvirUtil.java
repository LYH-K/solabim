package kr.co.chd.envir.device;

import java.io.IOException;
import org.apache.logging.log4j.*;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.i2c.I2CBus;


public class MeasureCropEnvirUtil {
    private static float illuminance;
    private static final int time = 2000;
    private static final Logger logger = LogManager.getLogger(MeasureCropEnvirUtil.class);


    //측정
    public CropEnvirInfo measure() {
        final GpioController gpio = GpioFactory.getInstance();

        CropEnvirInfo cropEnvirInfoMeasure = new CropEnvirInfo();

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
        try {
            gpio.setShutdownOptions(true, PinState.LOW, verticalpins);
            gpio.setShutdownOptions(true, PinState.LOW, horizontalpins);

            GpioStepperMotorComponent verticalMotor = new GpioStepperMotorComponent(verticalpins);
            GpioStepperMotorComponent horizontalMotor = new GpioStepperMotorComponent(horizontalpins);

            byte[] half_step_sequence = new byte[8];
            half_step_sequence[0] = (byte) 0b0001;
            half_step_sequence[1] = (byte) 0b0011;
            half_step_sequence[2] = (byte) 0b0010;
            half_step_sequence[3] = (byte) 0b0110;
            half_step_sequence[4] = (byte) 0b0100;
            half_step_sequence[5] = (byte) 0b1100;
            half_step_sequence[6] = (byte) 0b1000;
            half_step_sequence[7] = (byte) 0b1001;

            verticalMotor.setStepInterval(2);
            verticalMotor.setStepSequence(half_step_sequence);
            verticalMotor.setStepsPerRevolution(2038);

            horizontalMotor.setStepInterval(2);
            horizontalMotor.setStepSequence(half_step_sequence);
            horizontalMotor.setStepsPerRevolution(2038);

            //세로축 각도 최대각도 찾기
            logger.debug("maxVerticalAngleStart");
            int maxVerticalAngle = maxVerticalAngle(verticalMotor);

            //최대 각도로 돌아가기
            logger.debug("maxVerticalAngle : " + maxVerticalAngle);
            logger.debug("maxVerticalReturn");
            maxVerticalMotor(maxVerticalAngle, verticalMotor);

            //가로축 모터 최대 가로축 각도 및 조도세기 넣기
            logger.debug("maxHorizontalAngleStart");
            int maxHorizontalAngle = maxHorizontalMotor(horizontalMotor);
            horizontalMotor(horizontalMotor);

            //보내줄 내용 저장
            cropEnvirInfoMeasure.setVerticalAngle(maxVerticalAngle);
            cropEnvirInfoMeasure.setHorizontalAngle(maxHorizontalAngle);
            cropEnvirInfoMeasure.setIlluminance(illuminance);

            verticalReset(cropEnvirInfoMeasure.getVerticalAngle(), verticalMotor);
            horizontalReset(horizontalMotor);

            logger.debug("resultvertical : " + cropEnvirInfoMeasure.getVerticalAngle());
            logger.debug("resulthorizontal : " + cropEnvirInfoMeasure.getHorizontalAngle());
            logger.debug("resultillumant : " + cropEnvirInfoMeasure.getIlluminance());

            verticalMotor.stop();
            horizontalMotor.stop();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            gpio.shutdown();
            gpio.unprovisionPin(verticalpins);
            gpio.unprovisionPin(horizontalpins);
        }
        return cropEnvirInfoMeasure;
    }

    //세로축 최대값 구하기
    private int maxVerticalAngle(GpioStepperMotorComponent verticalMotor) throws Exception {
        CropEnvirInfo[] cropEnvirInfos = new CropEnvirInfo[6];
        logger.debug("verticalAngle : verticalIlluminance");
        for(int i = 0; i < 6; i++) {
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

            cropEnvirInfos[i].setVerticalAngle((i + 1) * 30);
            cropEnvirInfos[i].setIlluminance(illuminance);
            if(i > 0) {
                logger.debug((cropEnvirInfos[i].getVerticalAngle() - 30) + " : " + cropEnvirInfos[i].getIlluminance());
            }
        }

        float max = cropEnvirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 1; i < 6; i++) {
            if(max < cropEnvirInfos[i].getIlluminance()) {
                max = cropEnvirInfos[i].getIlluminance();
                angle = cropEnvirInfos[i - 1].getVerticalAngle();
            }
        }

        return angle;
    }

    //조도 측정
    private float illuminanceMeasurement(){
        IlluminanceUtil bh1750fvi = null;
        try {
            Thread.sleep(time);

            bh1750fvi = IlluminanceUtil.getInstance(I2CBus.BUS_1, IlluminanceUtil.I2C_ADDRESS_23);

            illuminance = bh1750fvi.getOptical();

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
        verticalMotor.step(-335);
        Thread.sleep(time);
    }

    //세로축 각도 최대 위치로
    private void maxVerticalMotor(int MaxAngle, GpioStepperMotorComponent verticalMotor){
        int angle = (((180 - MaxAngle)) / 30) * 330;
        verticalMotor.step(angle);
    }

    //가로축 각도
    private void horizontalMotor(GpioStepperMotorComponent horizontalMotor) throws InterruptedException {
        horizontalMotor.step(330);
        Thread.sleep(time);
    }

    //가로축 각도 최대각도
    private int maxHorizontalMotor(GpioStepperMotorComponent horizontalMotor) throws Exception {

        CropEnvirInfo[] cropEnvirInfos = new CropEnvirInfo[12];
        logger.debug("HorizontalAngle : HorizontalIlluminance");

        for(int i = 0; i < 12; i++) {
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

            cropEnvirInfos[i].setHorizontalAngle((i + 1) * 30);
            cropEnvirInfos[i].setIlluminance(illuminance);
            if(0 < i){
                logger.debug((cropEnvirInfos[i].getHorizontalAngle() - 30) + " : " + cropEnvirInfos[i].getIlluminance());
            }
            Thread.sleep(1000);
        }

        logger.debug("360 : " + cropEnvirInfos[0].getIlluminance());


        float max = cropEnvirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 1; i < 12; i++) {
            if(max < cropEnvirInfos[i].getIlluminance()) {
                max = cropEnvirInfos[i].getIlluminance();
                angle = cropEnvirInfos[i - 1].getHorizontalAngle();
            }
        }

        illuminance = max;

        return angle;
    }

    //세로축 각도 초기화
    private void verticalReset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = ((maxAngle / 30)) * 330;
        motor.step(angle);
    }

    //가로축 각도 초기화
    private void horizontalReset(GpioStepperMotorComponent motor) {
        motor.step(-4079);
    }
}