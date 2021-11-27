package kr.co.chd.facility;

import java.io.Serializable;

public class EnvirInfo implements Serializable {
    private int horizonAngle;
    private int verticalAngle;
    private boolean resetSignal;

    public EnvirInfo() {
    }

    public int getHorizonAngle() {
        return horizonAngle;
    }

    public void setHorizonAngle(int horizonAngle) {
        this.horizonAngle = horizonAngle;
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
