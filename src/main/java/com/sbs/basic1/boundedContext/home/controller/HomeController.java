package com.sbs.basic1.boundedContext.home.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링부트에게 해당 클래스는 컨트롤러라고 명시
public class HomeController {
  private int id;

  public HomeController() {
    id = -1;
  }


  @GetMapping("/home/main")
  // http://localhost:8080/home/main 요청이 GET 요청으로 들어옴
  // 아래 메서드를 실행
  @ResponseBody  // 메서드의 실행 결과를 body에 답아 응답에 보냄
  public String showMain() {
    return "스프링 부트!";
  }

  @GetMapping("/home/main2")
  @ResponseBody
  public String showMain2() {
    return "안녕하세요.";
  }

  @GetMapping("/home/main3")
  @ResponseBody
  public String showMain3() {
    return "스프링부트는 재밌습니까?";
  }

  @GetMapping("/home/increase")
  @ResponseBody // 데이터를 담아 응답에 보냄, 보낸 데이터는 모두 문자열이다.
  public int showIncrease() {
    return ++id;
  }

  @GetMapping("/home/decrease")
  @ResponseBody // 데이터를 담아 응답에 보냄, 보낸 데이터는 모두 문자열이다.
  public int showDecrease() {
    return --id;
  }

  @GetMapping("/home/initNumber")
  @ResponseBody // 데이터를 담아 응답에 보냄, 보낸 데이터는 모두 문자열이다.
  public int showInitNumber() {
    id = 0;
    return id;
  }

  @GetMapping("/home/plus")
  @ResponseBody
  public int showPlus(int a, int b) {
    /*
    // 서블릿 방식
    int a = Integer.parseInt(req.getParameter("a"));
    int b = Integer.parseInt(req.getParameter("b"));

    req.setAttribute("a", a);
    req.setAttribute("b", b);
    */
    
    // @RequestParam(defaultValue = "0") : 매개변수에 기본값을 부여
    // 기본값을 부여하지 않는 경우에 생략 가능하다.
    return a + b;
  }
}
