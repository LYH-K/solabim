package kr.co.chd.envir.weatherinfo;

public interface SunTimeService {
    public SunTimeInfo searchSunTime() throws Exception; //일출 및 일몰 조회
    public boolean resetSignal(SunTimeInfo sunTimeInfo) throws Exception; // 위치 초기화 신호
}
