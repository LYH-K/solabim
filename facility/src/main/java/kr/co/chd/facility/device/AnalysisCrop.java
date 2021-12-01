package kr.co.chd.facility.device;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AnalysisCrop {
    public String analysisCrop() {
        String[] command = new String[2];
        command[0] = "python";
        command[1] = "/home/pi/Desktop/camera.py";

        String cropRGB = null;

        try {
            cropRGB = byProcessBuilder(command);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return cropRGB;
    }

    private String byProcessBuilder(String[] command)
            throws IOException,InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        return printStream(process);
    }

    private String printStream(Process process)
            throws IOException, InterruptedException {
        process.waitFor();
        String cropRGB = null;
        try (InputStream pythonResult = process.getInputStream()) {
            cropRGB = readPythonResult(pythonResult, System.out);
        }

        return cropRGB;
    }

    private String readPythonResult(InputStream input, OutputStream output) throws IOException {
        String cropRGB = null;

        try {
            String steamToString = IOUtils.toString(input);

            String[] rgbs = steamToString.split("'");

            cropRGB = rgbs[0];
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cropRGB;
    }
}
