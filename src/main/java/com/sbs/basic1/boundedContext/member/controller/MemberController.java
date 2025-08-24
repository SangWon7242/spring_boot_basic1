package com.sbs.basic1.boundedContext.member.controller;

import com.sbs.basic1.boundedContext.member.entity.Member;
import com.sbs.basic1.boundedContext.member.service.MemberService;
import com.sbs.basic1.gloabal.base.rsData.RsData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;
  
  // 생성자 주입
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/login")
  @ResponseBody
  public RsData login(String username, String password, HttpServletResponse resp) {
    if(username == null || username.trim().isEmpty()) {
      return RsData.of("F-1", "아이디를 입력해주세요.");
    }

    if(password == null || password.trim().isEmpty()) {
      return RsData.of("F-2", "비밀번호를 입력해주세요.");
    }

    RsData rsData = memberService.tryLogin(username, password);

    if(rsData.isSuccess()) {
      long memberId = (long) rsData.getData();
      resp.addCookie(new Cookie("loginedMemberId", memberId + ""));
    }

    return rsData;
  }

  @GetMapping("/logout")
  @ResponseBody
  public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
    if(req.getCookies() != null) {
      Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("loginedMemberId"))
          .forEach(cookie -> {
            cookie.setMaxAge(0);
            resp.addCookie(cookie); // 만료한 쿠키를 다시 등록
          });
    }

    return RsData.of("S-1", "로그아웃 되었습니다.");
  }

  @GetMapping("/profile")
  @ResponseBody
  public RsData showProfile(HttpServletRequest req) {
    long loginedMemberId = 0;
    if(req.getCookies() != null) {
      loginedMemberId = Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("loginedMemberId"))
          .map(Cookie::getValue)
          .map(Long::parseLong)
          .findFirst()
          .orElse(0L);
    }

    boolean isLogined = loginedMemberId > 0;

    if(!isLogined) {
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }

    Member member = memberService.findById(loginedMemberId);

    return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(member.getUsername()));
  }
}
