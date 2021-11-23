package kr.co.system.chd.analysisManagement.model;

import java.io.Serializable;

public class CropAnalysis implements Serializable {
    String date;
    int analysisNo;
    int growth;
    String verticalImage;
    String horizontalImage;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAnalysisNo() {
        return analysisNo;
    }

    public void setAnalysisNo(int analysisNo) {
        this.analysisNo = analysisNo;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public CropAnalysis() {
    }

    public String getVerticalImage() {
        return verticalImage;
    }

    public void setVerticalImage(String verticalImage) {
        this.verticalImage = verticalImage;
    }

    public String getHorizontalImage() {
        return horizontalImage;
    }

    public void setHorizontalImage(String horizontalImage) {
        this.horizontalImage = horizontalImage;
    }
}
