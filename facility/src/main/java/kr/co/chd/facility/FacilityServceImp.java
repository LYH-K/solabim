package kr.co.chd.facility;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import org.springframework.stereotype.Service;

@Service
public class FacilityServceImp implements FacilityService{

    @Override
    public EnvirInfo receiveFacilityInfo(EnvirInfo envirInfo) {
        return null;
    }

    @Override
    public void controlFacility(EnvirInfo envirInfo) throws InterruptedException {
        int verticalAngle = envirInfo.getVerticalAngle();//세로축 각도
        int horizontalAngle = envirInfo.getVerticalAngle();

        System.out.println("세로축 : " + verticalAngle +"\n 가로축"+ horizontalAngle);

        final GpioController gpio = GpioFactory.getInstance();

        final GpioPinDigitalOutput[] pins = {
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
                gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW) };

        gpio.setShutdownOptions(true, PinState.LOW, pins);

        GpioStepperMotorComponent motor = new GpioStepperMotorComponent(pins);

        byte[] single_step_sequence = new byte[4];
        single_step_sequence[0] = (byte) 0b0001;
        single_step_sequence[1] = (byte) 0b0010;
        single_step_sequence[2] = (byte) 0b0100;
        single_step_sequence[3] = (byte) 0b1000;

        motor.setStepInterval(2); // 움직이는 시간 간격 2초가 제일 빠르고 뒤로 갈 수록 느려짐
        motor.setStepSequence(single_step_sequence); //스텝 방식 설정

        motor.setStepsPerRevolution(2038);//1바퀴를 360도 (2038)


        System.out.println("   Motor FORWARD for 2038 steps.");
        motor.step(2038);//좌로 1바퀴
        System.out.println("   Motor STOPPED for 2 seconds.");
        Thread.sleep(2000);


        System.out.println("   Motor REVERSE for 2038 steps.");
        motor.step(-2038);//우로 1바퀴
        System.out.println("   Motor STOPPED for 2 seconds.");
        Thread.sleep(2000);


        // final stop to ensure no motor activity
        motor.stop();

        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and
        // scheduled tasks)
        gpio.shutdown();

    }

    @Override
    public CropAnalysis analysisCrop() {
        return null;
    }

    @Override
    public void sendCropInfo(CropAnalysis cropAnalysis) {

    }
}
