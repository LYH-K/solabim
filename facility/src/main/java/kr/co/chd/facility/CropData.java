package kr.co.chd.facility;

import java.io.Serializable;

public class CropData implements Serializable {
    private int cropSideR;
    private int cropSideB;
    private int cropSideG;
    private int cropVerticalR;
    private int cropVerticalB;
    private int cropVerticalG;

    public CropData() {
    }

    public int getCropSideR() {
        return cropSideR;
    }

    public void setCropSideR(int cropSideR) {
        this.cropSideR = cropSideR;
    }

    public int getCropSideB() {
        return cropSideB;
    }

    public void setCropSideB(int cropSideB) {
        this.cropSideB = cropSideB;
    }

    public int getCropSideG() {
        return cropSideG;
    }

    public void setCropSideG(int cropSideG) {
        this.cropSideG = cropSideG;
    }

    public int getCropVerticalR() {
        return cropVerticalR;
    }

    public void setCropVerticalR(int cropVerticalR) {
        this.cropVerticalR = cropVerticalR;
    }

    public int getCropVerticalB() {
        return cropVerticalB;
    }

    public void setCropVerticalB(int cropVerticalB) {
        this.cropVerticalB = cropVerticalB;
    }

    public int getCropVerticalG() {
        return cropVerticalG;
    }

    public void setCropVerticalG(int cropVerticalG) {
        this.cropVerticalG = cropVerticalG;
    }
}
