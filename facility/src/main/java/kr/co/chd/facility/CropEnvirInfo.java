package kr.co.chd.facility;

import java.io.Serializable;

public class CropEnvirInfo implements Serializable {
    private int horizontalAngle;
    private int verticalAngle;
    private boolean resetSignal;

    public CropEnvirInfo() {
    }

    public int getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(int horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public int getVerticalAngle() {
        return verticalAngle;
    }

    public void setVerticalAngle(int verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    public boolean isResetSignal() {
        return resetSignal;
    }

    public void setResetSignal(boolean resetSignal) {
        this.resetSignal = resetSignal;
    }
}
