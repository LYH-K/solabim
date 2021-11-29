package kr.co.chd.envir.envir_management;

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
			byProcessBuilder(command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void byProcessBuilder(String[] command)
			throws IOException,InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		printStream(process);
	}

	private void printStream(Process process)
			throws IOException, InterruptedException {
		process.waitFor();
		try (InputStream pythonResult = process.getInputStream()) {
			readPythonResult(pythonResult, System.out);
		}
	}

	private void readPythonResult(InputStream input, OutputStream output) throws IOException {
		try {
			String steamToString = IOUtils.toString(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
