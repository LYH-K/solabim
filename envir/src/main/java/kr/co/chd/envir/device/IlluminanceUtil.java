package kr.co.chd.envir.device;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class IlluminanceUtil {

    public static final byte I2C_ADDRESS_23 = 0x23;
    public static final byte I2C_ADDRESS_5C = 0x5c;

    private static final byte OPECODE_POWER_DOWN							= 0x00;
    private static final byte OPECODE_POWER_ON								= 0x01;
    private static final byte OPECODE_RESET									= 0x07;
    private static final byte OPECODE_CONTINUOUSLY_H_RESOLUTION_MODE		= 0x10;
    private static final byte OPECODE_CONTINUOUSLY_H_RESOLUTION_MODE2	= 0x11;
    private static final byte OPECODE_CONTINUOUSLY_L_RESOLUTION_MODE		= 0x13;
    private static final byte OPECODE_ONE_TIME_H_RESOLUTION_MODE			= 0x20;
    private static final byte OPECODE_ONE_TIME_H_RESOLUTION_MODE2		= 0x21;
    private static final byte OPECODE_ONE_TIME_L_RESOLUTION_MODE			= 0x23;

    private static final int H_RESOLUTION_MODE_MEASUREMENT_TIME_MILLIS	= 120;
    private static final int H_RESOLUTION_MODE2_MEASUREMENT_TIME_MILLIS	= 120;
    private static final int L_RESOLUTION_MODE_MEASUREMENT_TIME_MILLIS	= 16;

    private static final int SENSOR_DATA_LENGTH = 2;

    private final byte i2cAddress;
    private final I2CBus i2cBus;
    private final I2CDevice i2cDevice;
    private final String i2cName;
    private final String logPrefix;

    public static void main(String[] args) {
        illuminanceMeasurement();
    }

    private final AtomicInteger useCount = new AtomicInteger(0);

    private static final ConcurrentHashMap<String, IlluminanceUtil> map = new ConcurrentHashMap<String, IlluminanceUtil>();

    synchronized public static IlluminanceUtil getInstance(int i2cBusNumber, byte i2cAddress) {
        String key = i2cBusNumber + ":" + String.format("%x", i2cAddress);
        IlluminanceUtil bh1750fvi = map.get(key);
        if (bh1750fvi == null) {
            bh1750fvi = new IlluminanceUtil(i2cBusNumber, i2cAddress);
            map.put(key, bh1750fvi);
        }
        return bh1750fvi;
    }

    private IlluminanceUtil(int i2cBusNumber, byte i2cAddress) {
        if (i2cBusNumber != I2CBus.BUS_0 && i2cBusNumber != I2CBus.BUS_1) {
            throw new IllegalArgumentException("The set " + i2cBusNumber + " is not " +
                    I2CBus.BUS_0 + " or " + I2CBus.BUS_1 + ".");
        }
        if (i2cAddress == I2C_ADDRESS_23 || i2cAddress == I2C_ADDRESS_5C) {
            this.i2cAddress = i2cAddress;
        } else {
            throw new IllegalArgumentException("The set " + String.format("%x", i2cAddress) + " is not " +
                    String.format("%x", I2C_ADDRESS_23) + " or " + String.format("%x", I2C_ADDRESS_5C) + ".");
        }

        i2cName = "I2C_" + i2cBusNumber + "_" + String.format("%x", i2cAddress);
        logPrefix = "[" + i2cName + "] ";

        try {
            this.i2cBus = I2CFactory.getInstance(i2cBusNumber);
            this.i2cDevice = i2cBus.getDevice(i2cAddress);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    synchronized public void close() throws IOException {
        try {
            if (useCount.compareAndSet(1, 0)) {
                i2cBus.close();
                System.out.println(logPrefix);
            }
        } finally {
        }
    }

    private void dump(byte data, String tag) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%02x", data));
    }

    private void dump(byte[] data, String tag) {
        StringBuffer sb = new StringBuffer();
        for (byte data1 : data) {
            sb.append(String.format("%02x ", data1));
        }
    }

    private void write(byte out) throws IOException {
        try {
            dump(out, "BH1750FVI sensor command: write: ");
            i2cDevice.write(out);
        } catch (IOException e) {
        }
    }

    private byte[] read(int length) throws IOException {
        try {
            byte[] in = new byte[length];
            i2cDevice.read(in, 0, length);
            dump(in, "BH1750FVI sensor command: read:  ");
            return in;
        } catch (IOException e) {
            String message = logPrefix + "failed to read.";
            System.out.println(message);
            throw new IOException(message, e);
        }
    }

    public float getOptical() throws IOException {
        write(OPECODE_ONE_TIME_H_RESOLUTION_MODE);

        try {
            Thread.sleep(H_RESOLUTION_MODE_MEASUREMENT_TIME_MILLIS);
        } catch (InterruptedException e) {
        }

        byte[] data = read(SENSOR_DATA_LENGTH);

        return (float)((((int)(data[0] & 0xff) << 8) + (int)(data[1] & 0xff)) / 1.2);
    }

    private static float illuminanceMeasurement(){
        float illuminance = 0;
        IlluminanceUtil bh1750fvi = null;
        try {

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
}
