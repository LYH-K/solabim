package kr.co.envir.chd.publicdata;

import java.io.Serializable;

public class EnvirInfo implements Serializable {
    private int illuminance;
    private int verticalAngle;
    private int horizontalAngle;

    public EnvirInfo(){

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
}
