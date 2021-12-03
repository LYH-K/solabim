package kr.co.chd.facility.device;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;

public class MotorDriver {
    CropAnalysisInfoSender cropAnalysisInfoSender = new CropAnalysisInfoSender();
    private int horizontalAngle = 0;
    private int verticalAngle = 0;
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
        horizontalAngle = Integer.parseInt(parseInfo[0]);
        verticalAngle = Integer.parseInt(parseInfo[1]);
        boolean resetSignal = Boolean.parseBoolean(parseInfo[2]);

        System.out.println(horizontalAngle);

        gpio.setShutdownOptions(true, PinState.LOW, verticalpins);
        gpio.setShutdownOptions(true, PinState.LOW, horizontalpins);

        byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;

        verticalmotor.setStepsPerRevolution(2038);//1바퀴를 360도 (2038)
        horizontalmotor.setStepsPerRevolution(2038);//1바퀴를 360도 (2038)

        verticalmotor.setStepInterval(2); // 움직이는 시간 간격 2초가 제일 빠르고 뒤로 갈 수록 느려짐
        verticalmotor.setStepSequence(single_step_sequence); //스텝 방식 설정
        horizontalmotor.setStepInterval(2); // 움직이는 시간 간격 2초가 제일 빠르고 뒤로 갈 수록 느려짐
        horizontalmotor.setStepSequence(single_step_sequence); //스텝 방식 설정

        System.out.println("   Motor FORWARD for 2 revolutions.");
        verticalmotor.rotate(1);//setStepsPerRevolution에 설정한 만큼 1바퀴
        System.out.println("   Motor STOPPED for 2 seconds.");
        Thread.sleep(2000);

        System.out.println("   Motor FORWARD for 2 revolutions.");
        horizontalmotor.rotate(1);//setStepsPerRevolution에 설정한 만큼 1바퀴
        System.out.println("   Motor STOPPED for 2 seconds.");
        Thread.sleep(2000);

        verticalmotor.stop();
        horizontalmotor.stop();
        gpio.shutdown();
        
        if(resetSignal != true){
            AnalysisCrop analysisCrop = new AnalysisCrop();
            String cropRGB = analysisCrop.analysisCrop();
            System.out.println("clear");
            cropAnalysisInfoSender.sendCropAnalysisInfo(cropRGB);
        }
    }

}
