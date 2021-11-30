package kr.co.chd.envir.weather_info;

import java.time.LocalTime;

public class SunTimeInfo {
    private LocalTime sunRise;
    private LocalTime sunSet;
    private boolean resetSignal;;

    public SunTimeInfo(){
    }

    public void setResetSignal(boolean resetSignal) {
        this.resetSignal = resetSignal;
    }

    public boolean isResetSignal() {
        return resetSignal;
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
