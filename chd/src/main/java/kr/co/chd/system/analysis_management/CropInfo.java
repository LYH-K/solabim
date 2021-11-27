package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class CropInfo implements Serializable {
    private String date;
    private String time;
    private int growth;
    private int lux;
    private int horizontalAngle;
    private int verticalAngle;
    private String cropSideImageURL;
    private String cropVerticalImageURL;

    public CropInfo(){
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getLux() {
        return lux;
    }

    public void setLux(int lux) {
        this.lux = lux;
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

    public int getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(int horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public int getVerticalAngle() {
        return verticalAngle;
    }

    public void setVerticalAngle(int verticalAngle) {
        this.verticalAngle = verticalAngle;
    }
}
