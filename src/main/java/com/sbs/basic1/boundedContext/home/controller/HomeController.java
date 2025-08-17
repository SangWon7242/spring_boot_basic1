package com.sbs.basic1.boundedContext.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링부트에게 해당 클래스는 컨트롤러라고 명시
public class HomeController {
  @GetMapping("/home/main")
  // http://localhost:8080/home/main 요청이 GET 요청으로 들어옴
  // 아래 메서드를 실행
  @ResponseBody  // 메서드의 실행 결과를 body에 답아 응답에 보냄
  public String showMain() {
    return "스프링 부트!";
  }
}
