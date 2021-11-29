package kr.co.chd.envir.envir_management;

import java.io.Serializable;

public class EnvirInfo implements Serializable {
    private int envirNo;
    private int illuminance;
    private int verticalAngle;
    private int horizontalAngle;
    private boolean resetSignal;

    public EnvirInfo(){
    }

    public int getEnvirNo() {
        return envirNo;
    }

    public void setEnvirNo(int envirNo) {
        this.envirNo = envirNo;
    }

    public int getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(int illuminance) {
        this.illuminance = illuminance;
    }

    public int getVerticalAngle() {
        return verticalAngle;
    }

    public void setVerticalAngle(int verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    public int getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(int horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public boolean isResetSignal() {
        return resetSignal;
    }

    public void setResetSignal(boolean resetSignal) {
        this.resetSignal = resetSignal;
    }
}
