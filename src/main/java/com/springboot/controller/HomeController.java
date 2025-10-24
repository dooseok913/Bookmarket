package com.springboot.controller;

import com.springboot.domain.Member;
import com.springboot.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/")
//    Authentication authentication  - Spring Security가 로그인된 사용자의 인증 정보를 담는 객체입니다.
//    컨트롤러에서 Authentication authentication을 파라미터로 선언하면,//Spring Security가 알아서 로그인 정보를 넣어줍니다.
    public String welcome(Model model, Authentication authentication , HttpServletRequest httpServletRequest) {
        if(authentication == null)
            return "welcome";

        User user = (User) authentication.getPrincipal();
        String userId = user.getUsername();
        if(userId == null)
            return  "redirect:/login";
        Member member = memberService.getMemberById(userId);
//     HttpSession session = httpServletRequest.getSession(true); 세션을 생성하거나 가져오는 코드입니다.세션(Session)은 브라우저와 서버 간의 “로그인 상태”를 유지하기 위한 공간입니다. getSession(true)는 “이미 있으면 가져오고, 없으면 새로 만들어라”는 의미입니다.
        HttpSession session = httpServletRequest.getSession(true);
        //session.setAttribute("userLoginInfo", member);  세션에 로그인한 회원 정보를 저장합니다.세션은 Model보다 더 오래 유지됩니다.  Model은 현재 요청(request)에서만 유지되고,세션은 브라우저가 닫히거나 로그아웃하기 전까지 유지됩니다.
        session.setAttribute("userLoginInfo", member);
        return  "welcome";
    }



}
