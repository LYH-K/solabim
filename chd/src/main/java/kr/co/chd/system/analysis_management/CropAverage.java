package kr.co.chd.system.analysis_management;

import java.io.Serializable;

public class CropAverage implements Serializable {
    String date;
    int growthAvg;
    int illuminaceAvg;

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

    public int getIlluminaceAvg() {
        return illuminaceAvg;
    }

    public void setIlluminaceAvg(int illuminaceAvg) {
        this.illuminaceAvg = illuminaceAvg;
    }
}
