package kr.co.chd.facility;

import java.io.Serializable;

public class CropData implements Serializable {
    int dataNo;
    int RGBData;

    public CropData() {
    }

    public int getDataNo() {
        return dataNo;
    }

    public void setDataNo(int dataNo) {
        this.dataNo = dataNo;
    }

    public int getRGBData() {
        return RGBData;
    }

    public void setRGBData(int RGBData) {
        this.RGBData = RGBData;
    }
}
