package com.sbs.basic1.boundedContext.member.controller;

import com.sbs.basic1.boundedContext.member.entity.Member;
import com.sbs.basic1.boundedContext.member.input.MemberForm;
import com.sbs.basic1.boundedContext.member.service.MemberService;
import com.sbs.basic1.gloabal.base.rq.Rq.Rq;
import com.sbs.basic1.gloabal.base.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;
  private final Rq rq;

  @GetMapping("/login")
  public String showLogin() {
    return "member/login";
  }

  @PostMapping("/login")
  @ResponseBody
  public RsData login(MemberForm memberForm) {
    String username = memberForm.getUsername().trim();
    String password = memberForm.getPassword().trim();

    if(username == null || username.isEmpty()) {
      return RsData.of("F-1", "아이디를 입력해주세요.");
    }

    if(password == null || password.isEmpty()) {
      return RsData.of("F-2", "비밀번호를 입력해주세요.");
    }
    
    RsData rsData = memberService.tryLogin(memberForm.getUsername(), memberForm.getPassword());

    if(rsData.isSuccess()) {
      Member member = (Member) rsData.getData();
      rq.setSession("loginedMemberId", member.getId());
    }

    return rsData;
  }

  @GetMapping("/logout")
  @ResponseBody
  public RsData logout() {
    boolean cookieRemoved = rq.removeSession("loginedMemberId");

    if(!cookieRemoved) {
      return RsData.of("F-1", "로그아웃에 실패했습니다. 이미 로그아웃 상태입니다.");
    }

    return RsData.of("S-1", "로그아웃 되었습니다.");
  }

  @GetMapping("/profile")
  public String showProfile(Model model) {
    long loginedMemberId = rq.getLoginedMember();

    Member member = memberService.findById(loginedMemberId);

    model.addAttribute("member", member);

    return "member/profile";
  }

  @GetMapping("/session")
  @ResponseBody
  public String showSession() {
    return rq.getSessionDebugInfo().replaceAll("\n", "<br>");
  }
}
