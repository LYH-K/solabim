package kr.co.chd.envir.envir_management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class StartController {
    @Autowired
    EnvirService envirService;

    @GetMapping("start")
    public void start() throws Exception{
        envirService.mesureSend();
        envirService.now();
    }
}
