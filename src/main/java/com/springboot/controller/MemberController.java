package com.springboot.controller;

import com.springboot.domain.Member;
import com.springboot.domain.MemberFormDto;
import com.springboot.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/members")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/add")
    public String requestMemberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
    return  "member/addMember";
    }

    @PostMapping("/add")
//    Spring은 @Valid를 보면 내부적으로 자동으로 @ModelAttribute 처리를 함께 해줘요
    public  String submitAddNewMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "member/addMember";
        }
        try {
            Member member =Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/addMember";
        }
        return "redirect:/members";
    }

    @GetMapping("/update/{memberId}")
    public String requestUpdateMemberForm(@PathVariable(name="memberId") String memberId, Model model) {
        Member member =memberService.getMemberById(memberId);
        model.addAttribute("memberFormDto", member);
        return "member/updateMember";
    }

    @PostMapping("/update")
    public  String submitUpdateMember(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return  "member/updateMember";
        }
        try {
            memberService.updateMember(memberFormDto, passwordEncoder);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return  "member/addMember";
        }
        return "redirect:/members";
    }

    @GetMapping("/delete/{memberId}")
    public String deleteMember(@PathVariable(name="memberId") String memberId) {
        memberService.deleteMember(memberId);
        return "redirect:/logout";
    }



//    “/ (홈 주소)”로 새로 요청하라는 명령
    @GetMapping
    public String requestMain() {
        return "redirect:/";
    }




}
