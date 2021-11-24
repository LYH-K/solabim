package kr.co.facility.facility.model;

import java.io.Serializable;

public class CropAnalysis implements Serializable {
    int analysisNo;
    int horizonAngle;
    int verticalAngle;

    public CropAnalysis() {
    }

    public int getAnalysisNo() {
        return analysisNo;
    }

    public void setAnalysisNo(int analysisNo) {
        this.analysisNo = analysisNo;
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
