package system.co.kr.chd.model;

import java.io.Serializable;

public class CropAverage implements Serializable {
    String date;
    int rgbAverage;
    float luxAverage;

    public CropAverage() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRgbAverage() {
        return rgbAverage;
    }

    public void setRgbAverage(int rgbAverage) {
        this.rgbAverage = rgbAverage;
    }

    public float getLuxAverage() {
        return luxAverage;
    }

    public void setLuxAverage(float luxAverage) {
        this.luxAverage = luxAverage;
    }
}
