package kr.co.chd.facility.device;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;

public class MotorDriver {
    CropAnalysisInfoSender cropAnalysisInfoSender = new CropAnalysisInfoSender();
    private int horizontalAngle = 0;//현재 세로축 각도
    private int verticalAngle = 0;//현재 가로축 각도
    private int newHorizontalAngle = 0;//방금 측정된 세로축 각도
    private int newVerticalAngle = 0;//방금 측정된 가로축 각도
    private boolean resetSignal = false;

    final GpioController gpio = GpioFactory.getInstance();

    final GpioPinDigitalOutput[] verticalpins = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW)};

    final GpioPinDigitalOutput[] horizontalpins = {
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.LOW),
            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, PinState.LOW)};

    GpioStepperMotorComponent verticalmotor = new GpioStepperMotorComponent(verticalpins);
    GpioStepperMotorComponent horizontalmotor = new GpioStepperMotorComponent(horizontalpins);

    public void controlFacility(StringBuilder controlInfo) throws InterruptedException {
        String parseInfo[] = controlInfo.toString().split("/");
        newHorizontalAngle = Integer.parseInt(parseInfo[0]);//새로 측정된 세로축
        newVerticalAngle = Integer.parseInt(parseInfo[1]);//새로 측정된 가로축
        resetSignal = Boolean.parseBoolean(parseInfo[2]);

        gpio.setShutdownOptions(true, PinState.LOW, verticalpins);
        gpio.setShutdownOptions(true, PinState.LOW, horizontalpins);

        byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;

        verticalmotor.setStepInterval(2); // 움직이는 시간 간격 2초가 제일 빠르고 뒤로 갈 수록 느려짐
        verticalmotor.setStepSequence(single_step_sequence); //스텝 방식 설정
        horizontalmotor.setStepInterval(2); // 움직이는 시간 간격 2초가 제일 빠르고 뒤로 갈 수록 느려짐
        horizontalmotor.setStepSequence(single_step_sequence); //스텝 방식 설정

        if(resetSignal ==true){
            System.out.println("vertical change");
            verticalmotor.step((-verticalAngle)*6);
            Thread.sleep(2000);

            System.out.println("horizontal change");
            horizontalmotor.step((-horizontalAngle)*6);
            Thread.sleep(2000);

            verticalAngle = 0;
            horizontalAngle = 0;
        } else{
            System.out.println("vertical change");
            verticalmotor.step((-verticalAngle)*6);//세로축 각도를 0도로 변경한다.
            Thread.sleep(2000);

            System.out.println("horizontal change");
            horizontalmotor.step((newHorizontalAngle - horizontalAngle)*6); // 가로축 각도 변경
            Thread.sleep(2000);

            System.out.println("vertical change");
            verticalmotor.step(newVerticalAngle*6); //세로축 각도 변경
            Thread.sleep(2000);

            horizontalAngle = newHorizontalAngle;//현재 가로축 각도 업데이트
            verticalAngle = newVerticalAngle;//현재 세로축 각도 업데이트

            AnalysisCrop analysisCrop = new AnalysisCrop();
            String cropRGB = analysisCrop.analysisCrop();//사진 촬영 및 분석
            System.out.println("clear");

            cropAnalysisInfoSender.sendCropAnalysisInfo(cropRGB);
        }

        verticalmotor.stop();
        horizontalmotor.stop();
        gpio.shutdown();
    }
}


