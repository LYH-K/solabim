package kr.co.envir.chd.envirmanagement;

import com.pi4j.component.motor.impl.GpioStepperMotorComponent;
import com.pi4j.io.gpio.*;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

public class MeasureEnvirUtil {

    public EnvirInfo measureEnvirUtile() throws Exception {
        MeasureEnvirUtil measureEnvirUtil = new MeasureEnvirUtil();

        final GpioController horizontalgpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput[] horizontalpins = {
                horizontalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW),
                horizontalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW),
                horizontalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW),
                horizontalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW) };

        horizontalgpio.setShutdownOptions(true, PinState.LOW, horizontalpins);

        GpioStepperMotorComponent horizontalmotor = new GpioStepperMotorComponent(horizontalpins);

        byte[] horizontalsingle_step_sequence = new byte[4];
        horizontalsingle_step_sequence[0] = (byte) 0b0001;
        horizontalsingle_step_sequence[1] = (byte) 0b0010;
        horizontalsingle_step_sequence[2] = (byte) 0b0100;
        horizontalsingle_step_sequence[3] = (byte) 0b1000;

        horizontalmotor.setStepInterval(2);
        horizontalmotor.setStepSequence(horizontalsingle_step_sequence);
        horizontalmotor.setStepsPerRevolution(2038);

        measureEnvirUtil.excludingAngle(horizontalmotor);

        EnvirInfo[] horizontalEnvirInfo = new EnvirInfo[5];
        for(int i = 1; i < 5; i++) {
            int illuminance = measureEnvirUtil.horizontalAngle(horizontalmotor);
            horizontalEnvirInfo[i-1].setLux(illuminance);
        }

        //최대값 구하기
        int horizontalmax = horizontalEnvirInfo[0].getLux();
        for(int i = 0; i < 4; i++){
            if(horizontalmax < horizontalEnvirInfo[i].getLux()){
                int horizontAlangle = (i+1) * 30;
                horizontalEnvirInfo[0].setLux(horizontalEnvirInfo[1].getLux());
                horizontalEnvirInfo[0].setHorizontalAngle(horizontAlangle);
            }
        }

        EnvirInfo horizontalMaxEnvirInfo =  horizontalEnvirInfo[0];

        measureEnvirUtil.excludingAngle(horizontalmotor);

        measureEnvirUtil.changeHorizontalAxisPosition(horizontalMaxEnvirInfo, horizontalmotor);

        final GpioController verticalgpio = GpioFactory.getInstance();
        final GpioPinDigitalOutput[] pins = {
                verticalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, PinState.LOW),
                verticalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_27, PinState.LOW),
                verticalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_28, PinState.LOW),
                verticalgpio.provisionDigitalOutputPin(RaspiPin.GPIO_29, PinState.LOW)};

        verticalgpio.setShutdownOptions(true, PinState.LOW, pins);

        GpioStepperMotorComponent verticalmotor = new GpioStepperMotorComponent(pins);

        byte[] verticalsingle_step_sequence = new byte[8];
        verticalsingle_step_sequence[0] = (byte) 0b0001;
        verticalsingle_step_sequence[1] = (byte) 0b0010;
        verticalsingle_step_sequence[2] = (byte) 0b0011;
        verticalsingle_step_sequence[3] = (byte) 0b0100;

        verticalmotor.setStepInterval(2);
        verticalmotor.setStepSequence(verticalsingle_step_sequence);
        verticalmotor.setStepsPerRevolution(2038);

        measureEnvirUtil.excludingAngle(verticalmotor);

        EnvirInfo[] verticalEnvirInfo = new EnvirInfo[11];
        for(int i = 1; i < 12; i++){
            int illuminance = measureEnvirUtil.verticalAngle(verticalmotor);
            verticalEnvirInfo[i-1].setVerticalAngle(illuminance);
        }

        //최대값 구하기
        int verticalmax = verticalEnvirInfo[0].getLux();
        for(int i = 0; i < 11; i++){
            if(verticalmax < verticalEnvirInfo[i].getLux()){
                int verticalAlangle = (i+1) * 30;
                verticalEnvirInfo[0].setLux(verticalEnvirInfo[1].getLux());
                verticalEnvirInfo[0].setHorizontalAngle(verticalAlangle);
            }
        }

        verticalEnvirInfo[0].setHorizontalAngle(horizontalMaxEnvirInfo.getHorizontalAngle());

        EnvirInfo maxEnvirInfo = verticalEnvirInfo[0];

        measureEnvirUtil.resetAngle(maxEnvirInfo, horizontalmotor, verticalmotor);

        horizontalgpio.shutdown();
        verticalgpio.shutdown();

        return maxEnvirInfo;
    }
    
    //각도 예외
    private void excludingAngle(GpioStepperMotorComponent motor){
        motor.step(-336);
    }
    
    //세로축 제어
    private int horizontalAngle(GpioStepperMotorComponent motor) throws Exception {

        System.out.println("<--Pi4J--> GPIO Stepper Motor Example ... started.");

        System.out.println("   Motor REVERSE for 2038 steps.");
        motor.step(-336);
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return illuminanceMeasurement();
            }
        };

        Thread.sleep(10000);

        int illuminance = callable.call();

        return illuminance;
    }

    //세로축 가장 큰 조도 위치로 돌아가기
    private void changeHorizontalAxisPosition(EnvirInfo envirInfo, GpioStepperMotorComponent motor){
        int angle = (180 - envirInfo.getHorizontalAngle()) / 30 * 336;
        motor.step(angle);
    }

    //가로축 제어
    private int verticalAngle(GpioStepperMotorComponent motor) throws Exception {

        System.out.println("   Motor REVERSE for 2038 steps.");
        motor.step(-336);
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return illuminanceMeasurement();
            }
        };

        Thread.sleep(10000);

        int illuminance = callable.call();

        return illuminance;
    }

    //조도 측정
    public static int illuminanceMeasurement() throws IOException {
        System.out.println("Python Call");
        String[] command = new String[4];
        command[0] = "python";
        command[1] = "C:\\Users\\ydj29\\Desktop\\python\\LightSensor.py";

        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        System.out.println("output: " + outputStream.toString());

        return result;
    }
    
    //각도 초기화
    private void resetAngle(EnvirInfo envirInfo, GpioStepperMotorComponent horizontalmotor, GpioStepperMotorComponent verticalmotor){
        int horizontalAngle = (180 - envirInfo.getHorizontalAngle()) / 30 * 336;
        int verticalAngle = (360 - envirInfo.getVerticalAngle()) / 30 * 336;

        horizontalmotor.step(horizontalAngle);
        verticalmotor.step(verticalAngle);
    }
}

