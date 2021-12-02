package kr.co.chd.envir.management;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CropEnvirInfo implements Serializable {
    private int cropEnvirNo;
    private float illuminance;
    private int verticalAngle;
    private int horizontalAngle;
    private boolean resetSignal;

    public CropEnvirInfo(){
    }

    public int getCropEnvirNo() {
        return cropEnvirNo;
    }

    public void setCropEnvirNo(int cropEnvirNo) {
        this.cropEnvirNo = cropEnvirNo;
    }

    public float getIlluminance() {
        return illuminance;
    }

    public void setIlluminance(float illuminance) {
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

