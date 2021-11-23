package system.co.kr.chd.model;

import java.io.Serializable;

public class EnvirInfo implements Serializable {
    int envirNo;
    String date;
    int lux;
    int verticalAngle;
    int horizontalAngle;

    public int getEnvirNo() {
        return envirNo;
    }

    public void setEnvirNo(int envirNo) {
        this.envirNo = envirNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLux() {
        return lux;
    }

    public void setLux(int lux) {
        this.lux = lux;
    }

    public int getVerticalAngle() {
        return verticalAngle;
    }

    public void setVerticalAngle(int verticalAngle) {
        this.verticalAngle = verticalAngle;
    }

    public int getHorizontalAngle() {
        return horizontalAngle;
    }

    public void setHorizontalAngle(int horizontalAngle) {
        this.horizontalAngle = horizontalAngle;
    }

    public EnvirInfo() {
    }
}
