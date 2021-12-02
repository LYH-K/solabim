package kr.co.chd.envir.management;

import com.sun.scenario.effect.Crop;
import kr.co.chd.envir.weatherinfo.SunTimeInfo;
import kr.co.chd.envir.weatherinfo.SunTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MeasurementSchedulerImp implements MeasurementScheduler {
    @Autowired
    public SunTimeUtil sunTimeUtil;
    private Logger logger = (Logger) LogManager.getLogger(MeasurementSchedulerImp.class);

    @Override
    @Scheduled(cron = "*/1 * * * * *")
    public void getSunTimeInfo()  {
        System.out.println("되는가?");
    }
}
