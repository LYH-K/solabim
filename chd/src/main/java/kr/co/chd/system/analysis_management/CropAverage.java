package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class CropAverage implements Serializable {
    private String date;
    private int growthAvg;
    private int illuminanceAvg;

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

    public int getIlluminanceAvg() {
        return illuminanceAvg;
    }

    public void setIlluminanceAvg(int illuminanceAvg) {
        this.illuminanceAvg = illuminanceAvg;
    }
}
