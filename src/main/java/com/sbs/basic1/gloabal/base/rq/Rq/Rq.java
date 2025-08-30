package com.sbs.basic1.gloabal.base.rq.Rq;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

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

  public void setSession(String name, long value) {
    HttpSession session = req.getSession();
    session.setAttribute(name, value);
  }

  public boolean removeSession(String name) {
    HttpSession session = req.getSession();

    if(session.getAttribute(name) == null) return false;

    session.removeAttribute(name);

    return true;
  }

  public long getSessionAsLong(String name, long defaultValue) {
    try {
      long value = (long) req.getSession().getAttribute(name);
      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public String getSessionAsStr(String name, String defaultValue) {
    try {
      String value = (String) req.getSession().getAttribute(name);

      if(value == null) return defaultValue;

      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public String getSessionDebugInfo() {
    StringBuilder debug = new StringBuilder();
    HttpSession session = req.getSession(false);

    if (session == null) {
      return "No active session";
    }

    debug.append("Session Debug Information:\n");
    debug.append("------------------------\n");

    // 세션의 고유 식별자 - 서버에서 각 세션을 구분하기 위해 생성하는 유일한 ID
    debug.append("Session ID: ").append(session.getId()).append("\n");

    // 세션이 생성된 시간 - 사용자가 처음 웹사이트에 접속하여 세션이 시작된 시점
    debug.append("Creation Time: ").append(new java.util.Date(session.getCreationTime())).append("\n");

    // 세션에 마지막으로 접근한 시간 - 사용자가 마지막으로 서버에 요청을 보낸 시점
    debug.append("Last Accessed Time: ").append(new java.util.Date(session.getLastAccessedTime())).append("\n");

    // 세션 만료 시간(초) - 사용자가 비활성 상태로 유지될 수 있는 최대 시간
    debug.append("Max Inactive Interval: ").append(session.getMaxInactiveInterval()).append(" seconds\n");

    // 새로운 세션 여부 - 이번 요청에서 새로 생성된 세션인지 확인
    debug.append("Is New: ").append(session.isNew()).append("\n");

    debug.append("\nSession Attributes:\n");

    // 세션에 저장된 모든 속성들을 순회하여 출력 - 로그인 정보, 장바구니 등 사용자별 데이터
    Enumeration<String> attributeNames = session.getAttributeNames();
    while (attributeNames.hasMoreElements()) {
      String name = attributeNames.nextElement();
      Object value = session.getAttribute(name);
      debug.append(name).append(" = ").append(value).append("\n");
    }

    return debug.toString();
  }

  public long getLoginedMember() {
    return getSessionAsLong("loginedMemberId", 0);
  }
}
