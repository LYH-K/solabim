package kr.co.chd.facility.facility_control;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
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