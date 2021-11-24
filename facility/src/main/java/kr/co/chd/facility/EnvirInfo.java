package kr.co.chd.facility;

import java.io.Serializable;

public class EnvirInfo implements Serializable {
    int horizonAngle;
    int verticalAngle;

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
}
