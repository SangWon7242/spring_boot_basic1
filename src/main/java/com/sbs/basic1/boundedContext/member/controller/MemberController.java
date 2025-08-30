package com.sbs.basic1.boundedContext.member.controller;

import com.sbs.basic1.boundedContext.member.entity.Member;
import com.sbs.basic1.boundedContext.member.service.MemberService;
import com.sbs.basic1.gloabal.base.rq.Rq.Rq;
import com.sbs.basic1.gloabal.base.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;
  private final Rq rq;

  @GetMapping("/login")
  @ResponseBody
  public RsData login(String username, String password) {
    if(username == null || username.trim().isEmpty()) {
      return RsData.of("F-1", "아이디를 입력해주세요.");
    }

    if(password == null || password.trim().isEmpty()) {
      return RsData.of("F-2", "비밀번호를 입력해주세요.");
    }

    RsData rsData = memberService.tryLogin(username, password);

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
  @ResponseBody
  public RsData showProfile() {
    long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0L);

    boolean isLogined = loginedMemberId > 0;

    if(!isLogined) {
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }

    Member member = memberService.findById(loginedMemberId);

    return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(member.getUsername()));
  }

  @GetMapping("/session")
  @ResponseBody
  public String showSession() {
    return rq.getSessionDebugInfo().replaceAll("\n", "<br>");
  }
}
