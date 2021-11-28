package kr.co.envir.chd.envirmanagement;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;

public class MeasureEnvirUtil {
    public static void main(String[] args) throws InterruptedException {
        MeasureEnvirUtil measureEnvirUtil = new MeasureEnvirUtil();
        measureEnvirUtil.measure();
    }

    public EnvirInfo measure() throws InterruptedException {
        EnvirInfo envirInfo = new EnvirInfo();

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

        verticalMotor(verticalMotor);

        //최대 각도로 돌아가기
        maxVerticalMotor(envirInfo.getVerticalAngle(), verticalMotor);

        horizontalMotor(horizontalMotor);

         envirInfo = maxHorizontalMotor(horizontalMotor);
         envirInfo.setVerticalAngle(verticalAngle);

        verticalreset(envirInfo.getVerticalAngle(),verticalMotor);
        horizontalreset(envirInfo.getHorizontalAngle(), horizontalMotor);

        verticalMotor.stop();
        horizontalMotor.stop();

        gpio.shutdown();

        return envirInfo;
    }

    //세로축 최대값 구하기
    public int maxVerticalAngle(GpioStepperMotorComponent verticalMotor) throws InterruptedException {
        EnvirInfo[] envirInfos = new EnvirInfo[4];
        for(int i = 0; i < 4; i++) {
            verticalMotor(verticalMotor);
            int illuminance = illuminanceMeasurement();
            envirInfos[i].setVerticalAngle((i+1)*30);
            envirInfos[i].setIlluminance(illuminance);
        }

        int max = envirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 0; i < 4; i++) {
            if(max < envirInfos[i].getIlluminance()) {
                max = envirInfos[i].getIlluminance();
                angle = envirInfos[i].getVerticalAngle();
            }
        }

        if(angle > 60) {
            angle = 60;
        }

        return angle;
    }

    //조도 측정
    public int illuminanceMeasurement(){
        return 0;
    }

    //세로축 각도
    public void verticalMotor(GpioStepperMotorComponent verticalMotor) throws InterruptedException {
        verticalMotor.step(-336);
        Thread.sleep(10000);
    }

    //세로축 각도 최대 위치로
    public void maxVerticalMotor(int MaxAngle, GpioStepperMotorComponent verticalMotor){
        int angle = (180 - MaxAngle) / 30 * 336;
        verticalMotor.step(angle);
    }

    //가로축 각도
    public void horizontalMotor(GpioStepperMotorComponent horizontalMotor) throws InterruptedException {
        horizontalMotor.step(-336);
        Thread.sleep(10000);
    }

    //가로축 각도 최대각도
    public EnvirInfo maxHorizontalMotor(GpioStepperMotorComponent horizontalMotor) throws InterruptedException {

        EnvirInfo[] envirInfos = new EnvirInfo[11];

        for(int i = 0; i < 11; i++) {
            horizontalMotor(horizontalMotor);
            int illuminance = illuminanceMeasurement();
            envirInfos[i].setVerticalAngle((i+1)*30);
            envirInfos[i].setIlluminance(illuminance);
        }

        int max = envirInfos[0].getIlluminance();
        int angle = 0;
        for(int i = 0; i < 11; i++) {
            if(max < envirInfos[i].getIlluminance()) {
                max = envirInfos[i].getIlluminance();
                angle = envirInfos[i].getVerticalAngle();
            }
        }

        if(angle > 60) {
            angle = 60;
        }

        EnvirInfo envirInfo = new EnvirInfo();
        envirInfo.setIlluminance(max);
        envirInfo.setVerticalAngle(angle);

        return envirInfo;
    }

    //세로축 각도 초기화
    public void verticalreset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = (180 - maxAngle) / 30 * 336;
        motor.step(angle);
    }

    //가로축 각도 초기화
    public void horizontalreset(int maxAngle, GpioStepperMotorComponent motor) {
        int angle = (360 - maxAngle) / 30 * 336;
        motor.step(angle);
    }
}