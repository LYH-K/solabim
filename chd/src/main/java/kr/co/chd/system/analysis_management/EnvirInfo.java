package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class EnvirInfo implements Serializable {
    private int envirNo;
    private int lux;
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

    public int getLux() {
        return lux;
    }

    public void setLux(int lux) {
        this.lux = lux;
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
