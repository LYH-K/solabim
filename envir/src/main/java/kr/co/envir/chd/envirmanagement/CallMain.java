package kr.co.envir.chd.envirmanagement;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CallMain {
	public static void main(String[] args)  {
		System.out.println("Python Call");
		String[] command = new String[4];
		command[0] = "python";
		command[1] = "C:\\Users\\ydj29\\Desktop\\python\\LightSensor.py";
		try {
			execPython(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void execPython(String[] command) throws IOException, InterruptedException {
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

	}

}
