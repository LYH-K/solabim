package kr.co.system.chd.analysisManagement.model;

import java.io.Serializable;

public class CropAverage implements Serializable {
    String date;
    int growthAvg;
    int luxAvg;

    public CropAverage() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGrowthAvg() {
        return growthAvg;
    }

    public void setGrowthAvg(int growthAvg) {
        this.growthAvg = growthAvg;
    }

    public int getLuxAvg() {
        return luxAvg;
    }

    public void setLuxAvg(int luxAvg) {
        this.luxAvg = luxAvg;
    }
}
