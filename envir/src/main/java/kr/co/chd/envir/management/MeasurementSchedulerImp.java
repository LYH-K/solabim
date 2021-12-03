package kr.co.chd.envir.management;

import kr.co.chd.envir.device.WatchServiceDriver;
import kr.co.chd.envir.weatherinfo.SunTimeInfo;
import kr.co.chd.envir.weatherinfo.SunTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MeasurementSchedulerImp implements MeasurementScheduler {
    private Logger logger = (Logger) LogManager.getLogger(MeasurementSchedulerImp.class);

    @Autowired
    WatchServiceDriver watchServiceDriver;
    @Autowired
    public SunTimeUtil sunTimeUtil;


    @Override
    @Scheduled(fixedDelay = 1000 * 10)
    public void getSunTimeInfo() {
        try {
            System.out.println("---------------> ");

            // 1. 일출 및 일몰 시간을 조회
            //SunTimeInfo sunTimeInfo = sunTimeUtil.searchSunTime();
            //logger.debug(sunTimeInfo.toString());

            // 2. 조도 및 각도 측정

            // 3. 농작물 환경 정보 송신 (관리 시스템)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
