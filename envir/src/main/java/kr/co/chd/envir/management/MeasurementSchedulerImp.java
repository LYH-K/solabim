package kr.co.chd.envir.management;

import kr.co.chd.envir.weatherinfo.SunTimeInfo;
import kr.co.chd.envir.weatherinfo.SunTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MeasurementSchedulerImp implements  MeasurementScheduler {
//    private Logger logger = (Logger) LogManager.getLogger(MeasurementSchedulerImp.class);

//    @Override
//    @Scheduled(fixedRate = 10000)
//    public void getSunTimeInfo() {
//        try {
//            SunTimeInfo sunTimeInfo = sunTimeInfo = sunTimeUtil.getSunTime();
//            logger.debug(sunTimeInfo.getSunSet());
//            sunTimeUtil.resetSingnalWrite(sunTimeInfo);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
