package kr.co.chd.system.analysis_management;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class CropData implements Serializable {
    private int cropSideR;
    private int cropSideG;
    private int cropSideB;
    private int cropVerticalR;
    private int cropVerticalG;
    private int cropVerticalB;

    public int getCropSideR() {
        return cropSideR;
    }

    public void setCropSideR(int cropSideR) {
        this.cropSideR = cropSideR;
    }

    public int getCropSideG() {
        return cropSideG;
    }

    public void setCropSideG(int cropSideG) {
        this.cropSideG = cropSideG;
    }

    public int getCropSideB() {
        return cropSideB;
    }

    public void setCropSideB(int cropSideB) {
        this.cropSideB = cropSideB;
    }

    public int getCropVerticalR() {
        return cropVerticalR;
    }

    public void setCropVerticalR(int cropVerticalR) {
        this.cropVerticalR = cropVerticalR;
    }

    public int getCropVerticalG() {
        return cropVerticalG;
    }

    public void setCropVerticalG(int cropVerticalG) {
        this.cropVerticalG = cropVerticalG;
    }

    public int getCropVerticalB() {
        return cropVerticalB;
    }

    public void setCropVerticalB(int cropVerticalB) {
        this.cropVerticalB = cropVerticalB;
    }

    CropData() {
    }
}
