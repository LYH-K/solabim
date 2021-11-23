package system.co.kr.chd.model;

import java.io.Serializable;

public class CropAnalysis implements Serializable {
    int analysisNo;
    int growth;
    String cropSideImage;
    String cropVerticalImage;

    public CropAnalysis() {
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public String getCropSideImage() {
        return cropSideImage;
    }

    public void setCropSideImage(String cropSideImage) {
        this.cropSideImage = cropSideImage;
    }

    public String getCropVerticalImage() {
        return cropVerticalImage;
    }

    public void setCropVerticalImage(String cropVerticalImage) {
        this.cropVerticalImage = cropVerticalImage;
    }

    public int getAnalysisNo() {
        return analysisNo;
    }

    public void setAnalysisNo(int analysisNo) {
        this.analysisNo = analysisNo;
    }
}
