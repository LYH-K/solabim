package kr.co.chd.system.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class AccessServiceImp extends HandlerInterceptorAdapter implements AccessService {
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        HttpSession session = request.getSession(); //로그인 페이지 제외한 나머지 세션 체크
        if(session.getAttribute("sessionId") != null){
            return true;
        }

        response.sendRedirect("/chd/login");
        return false;
    }

    public void login(Manager manager, HttpSession session){
        if(searchManager(manager)){
            session.setAttribute("sessionId",manager.getId());//해당하는 아이디가 있으면 세션 생성
        }
    }

    public void logout(HttpSession session){
        session.invalidate();
    }

    public boolean searchManager(Manager manager){
        int check = managerMapper.select(manager);
        System.out.println(manager.getPassword());

        if(check ==1 ){
            return true;
        }

        return false;
    }
}
