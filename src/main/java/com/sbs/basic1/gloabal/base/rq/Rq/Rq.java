package com.sbs.basic1.gloabal.base.rq.Rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope // 해당 객체는 매 요청마다 생성
@AllArgsConstructor
public class Rq {
  private final HttpServletRequest req;
  private final HttpServletResponse resp;

  public void setCookie(String name, long value) {
    setCookie(name, value + "");
  }

  private void setCookie(String name, String value) {
    resp.addCookie(new Cookie(name, value));
  }

  public boolean removeCookie(String name) {
    Cookie cookie = Arrays.stream(req.getCookies())
        .filter(c -> c.getName().equals(name))
        .findFirst()
        .orElse(null);

    if(cookie != null) {
      cookie.setMaxAge(0);
      resp.addCookie(cookie);
    }

    return true;
  }

  public long getCookieAsLong(String name, long defaultValue) {
      String value = getCookie(name, null);

      if(value == null) return defaultValue;

      try {
        return Long.parseLong(value);
      } catch (NumberFormatException e) {
        return defaultValue;
      }
  }

  public String getCookie(String name, String defaultValue) {
    if(req.getCookies() == null) return defaultValue;

    return Arrays.stream(req.getCookies())
        .filter(cookie -> cookie.getName().equals("loginedMemberId"))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(defaultValue);
  }
}
