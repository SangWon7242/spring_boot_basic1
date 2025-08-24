package com.sbs.basic1.boundedContext.member.controller;

import com.sbs.basic1.boundedContext.member.entity.Member;
import com.sbs.basic1.boundedContext.member.service.MemberService;
import com.sbs.basic1.gloabal.base.rq.Rq.Rq;
import com.sbs.basic1.gloabal.base.rsData.RsData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/login")
  @ResponseBody
  public RsData login(String username, String password, HttpServletRequest req, HttpServletResponse resp) {
    Rq rq = new Rq(req, resp);

    if(username == null || username.trim().isEmpty()) {
      return RsData.of("F-1", "아이디를 입력해주세요.");
    }

    if(password == null || password.trim().isEmpty()) {
      return RsData.of("F-2", "비밀번호를 입력해주세요.");
    }

    RsData rsData = memberService.tryLogin(username, password);

    if(rsData.isSuccess()) {
      Member member = (Member) rsData.getData();
      rq.setCookie("loginedMemberId", member.getId());
    }

    return rsData;
  }

  @GetMapping("/logout")
  @ResponseBody
  public RsData logout(HttpServletRequest req, HttpServletResponse resp) {
    Rq rq = new Rq(req, resp);

    boolean cookieRemoved = rq.removeCookie("loginedMemberId");

    if(!cookieRemoved) {
      return RsData.of("F-1", "로그아웃에 실패했습니다. 이미 로그아웃 상태입니다.");
    }

    return RsData.of("S-1", "로그아웃 되었습니다.");
  }

  @GetMapping("/profile")
  @ResponseBody
  public RsData showProfile(HttpServletRequest req, HttpServletResponse resp) {
    Rq rq = new Rq(req, resp);

    long loginedMemberId = rq.getCookieAsLong("loginedMemberId", 0L);

    boolean isLogined = loginedMemberId > 0;

    if(!isLogined) {
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }

    Member member = memberService.findById(loginedMemberId);

    return RsData.of("S-1", "당신의 username(은)는 %s입니다.".formatted(member.getUsername()));
  }
}
