package kr.co.envir.chd.envirmanagement;

import java.time.LocalTime;

public class SunTimeInfo {
    private LocalTime sunRise;
    private LocalTime sunSet;

    public SunTimeInfo(){

    }

    public LocalTime getSunRise() {
        return sunRise;
    }

    public void setSunRise(LocalTime sunRise) {
        this.sunRise = sunRise;
    }

    public LocalTime getSunSet() {
        return sunSet;
    }

    public void setSunSet(LocalTime sunSet) {
        this.sunSet = sunSet;
    }
}
