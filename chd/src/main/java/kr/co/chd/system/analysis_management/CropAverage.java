package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class CropAverage implements Serializable {
    private String date;
    private int growthAvg;
    private int luxAvg;

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
