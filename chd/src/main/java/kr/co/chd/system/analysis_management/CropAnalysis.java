package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class CropAnalysis implements Serializable {
    private int growth;
    private String cropSideImageURL;
    private String cropVerticalImageURL;

    public CropAnalysis() {

    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public String getCropSideImageURL() {
        return cropSideImageURL;
    }

    public void setCropSideImageURL(String cropSideImageURL) {
        this.cropSideImageURL = cropSideImageURL;
    }

    public String getCropVerticalImageURL() {
        return cropVerticalImageURL;
    }

    public void setCropVerticalImageURL(String cropVerticalImageURL) {
        this.cropVerticalImageURL = cropVerticalImageURL;
    }
}
