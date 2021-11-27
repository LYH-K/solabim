package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class CropInfo implements Serializable {
    String date;
    int growth;
    int illuminace;
    int horizontalAngle;
    int verticalAngle;
    String horizontalImage;
    String verticalImage;

    public CropInfo(){
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public int getIlluminace() {
        return illuminace;
    }

    public void setIlluminace(int illuminace) {
        this.illuminace = illuminace;
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

    public String getHorizontalImage() {
        return horizontalImage;
    }

    public void setHorizontalImage(String horizontalImage) {
        this.horizontalImage = horizontalImage;
    }

    public String getVerticalImage() {
        return verticalImage;
    }

    public void setVerticalImage(String verticalImage) {
        this.verticalImage = verticalImage;
    }
}
