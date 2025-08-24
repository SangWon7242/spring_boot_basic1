package com.sbs.basic1.boundedContext.home.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

@Controller // 스프링부트에게 해당 클래스는 컨트롤러라고 명시
public class HomeController {
  private int id;
  List<Person> personList;

  public HomeController() {
    id = -1;
    personList = new ArrayList<>();
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

  @GetMapping("/home/plus2/{a}/{b}")
  @ResponseBody
  public int showPlus2(@PathVariable int a, @PathVariable int b) {
    return a + b;
  }

  @GetMapping("/home/returnBoolean")
  @ResponseBody
  public boolean showReturnBoolean() {
    return true;
  }

  @GetMapping("/home/returnDouble")
  @ResponseBody
  public double showReturnDouble() {
    return Math.PI;
  }

  @GetMapping("/home/returnArray")
  @ResponseBody
  public int[] showReturnArray() {
    int[] arr = {10, 20, 30, 40, 50};
    return arr;
  }

  @GetMapping("/home/returnList")
  @ResponseBody
  public List<Integer> showReturnList() {
    // List<Integer> list = List.of(10, 20, 30, 40, 50);

    /*
    List<Integer> list = new ArrayList<>(){{
      add(10);
      add(20);
      add(30);
      add(40);
      add(50);
    }};
    */

    List<Integer> list = new ArrayList<>();
    list.add(10);
    list.add(20);
    list.add(30);
    list.add(40);
    list.add(50);

    return list;
  }

  @GetMapping("/home/returnMap")
  @ResponseBody
  public Map<String, Object> showReturnMap() {
    /*
    Map<String, Object> map = Map.of(
        "name", "홍길동",
        "age", 30,
        "address", "서울시",
        "phone", "010-1234-5678",
        "email", "user1@test.com",
        "hobbies", List.of("독서", "수영", "요리")
    );
    */

    Map<String, Object> map = new LinkedHashMap<>() {{
      put("name", "홍길동");
      put("age", 30);
      put("address", "서울시");
      put("phone", "010-1234-5678");
      put("email", "user1@test.com");
      put("hobbies", List.of("독서", "수영", "요리"));
    }};

    return map;
  }

  @GetMapping("/home/returnMember")
  @ResponseBody
  public Member1 showReturnMember() {
    Member1 member = new Member1 (
        "홍길동",
        30,
        "서울시",
        "010-1234-5678",
        "user1@test.com",
        List.of("독서", "수영", "요리")
    );

    return member;
  }

  @GetMapping("/home/returnMember2")
  @ResponseBody
  public Member2 showReturnMember2() {
    Member2 member = new Member2(
        "홍길동",
        30,
        "서울시",
        "010-1234-5678",
        "user1@test.com",
        List.of("독서", "수영", "요리")
    );

    return member;
  }

  @GetMapping("/home/returnMemberMapList")
  @ResponseBody
  public List<Map<String, Object>> showReturnMemberMapList() {
    Map<String, Object> member1 = new LinkedHashMap<>() {{
      put("name", "홍길동");
      put("age", 30);
      put("address", "서울시");
      put("phone", "010-1234-5678");
      put("email", "user1@test.com");
      put("hobbies", List.of("독서", "수영", "요리"));
    }};

    Map<String, Object> member2 = new LinkedHashMap<>() {{
      put("name", "홍길순");
      put("age", 20);
      put("address", "대전광역시");
      put("phone", "010-1234-5678");
      put("email", "user2@test.com");
      put("hobbies", List.of("테니스", "배구", "볼링"));
    }};

    Map<String, Object> member3 = new LinkedHashMap<>() {{
      put("name", "임꺽정");
      put("age", 25);
      put("address", "안산시");
      put("phone", "010-1234-5678");
      put("email", "user3@test.com");
      put("hobbies", List.of("기타연주", "피아노연주", "바이올린연주"));
    }};

    List<Map<String, Object>> memberList = new ArrayList<>();
    memberList.add(member1);
    memberList.add(member2);
    memberList.add(member3);

    return memberList;
  }

  @GetMapping("/home/returnMemberList")
  @ResponseBody
  public List<Member2> showReturnMemberList() {
    List<Member2> memberList = new ArrayList<>();
    memberList.add(
        new Member2(
            "홍길동",
            30,
            "서울시",
            "010-1234-5678",
            "user1@test.com",
            List.of("독서", "수영", "요리"))
    );

    memberList.add(new Member2("홍길순", 20, "대전광역시", "010-1234-5678", "user2@test.com", List.of("테니스", "배구", "볼링")));
    memberList.add(new Member2("임꺽정", 25, "안산시", "010-1234-5678", "user3@test.com", List.of("기타연주", "피아노연주", "바이올린연주")));

    return memberList;
  }

  @GetMapping("/home/makePersonData")
  @ResponseBody
  public String makePersonData() {
    personList.add(new Person(11, "홍길동"));
    personList.add(new Person(22, "홍길순"));
    personList.add(new Person(33, "임꺽정"));
    return "사람 테스트 데이터 생성";
  }

  @GetMapping("/home/addPerson")
  @ResponseBody
  public String addPerson(String name, int age) {
    Person person = new Person(age, name);
    personList.add(person);
    return "%d번 사람이 추가되었습니다.".formatted(person.getId());
  }

  @GetMapping("/home/removePerson")
  @ResponseBody
  public String removePerson(int id) {
    /*
    // v1
    Person foundPerson = null;

    for(Person person : personList) {
      if(person.getId() == id) {
        foundPerson = person;
        break;
      }
    }

    if(foundPerson == null) return "%d번 사람은 존재하지 않습니다.".formatted(id);

    personList.remove(foundPerson);
    */

    // v2
    /*
    Person person = personList.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElse(null);

    if(person == null) return "%d번 사람은 존재하지 않습니다.".formatted(id);

    personList.remove(person);
    */

    // v3
    // 삭제 성공시 true를 반환, 실패시 false를 반환
    boolean removed = personList.removeIf(p -> p.getId() == id);

    if (!removed) return "%d번 사람은 존재하지 않습니다.".formatted(id);

    return "%d번 사람이 삭제되었습니다.".formatted(id);
  }

  @GetMapping("/home/modifyPerson")
  @ResponseBody
  public String modifyPerson(int id, String name, int age) {
    /*
    // v1
    Person foundPerson = null;

    for(Person person : personList) {
      if(person.getId() == id) {
        foundPerson = person;
        break;
      }
    }

    if(foundPerson == null) return "%d번 사람은 존재하지 않습니다.".formatted(id);
    */

    Person person = personList.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElse(null);

    if (person == null) return "%d번 사람은 존재하지 않습니다.".formatted(id);

    person.setName(name);
    person.setAge(age);

    return "%d번 사람이 수정되었습니다.".formatted(id);
  }

  @GetMapping("/home/showPeople")
  @ResponseBody
  public List<Person> showPeople() {
    return personList;
  }

  @GetMapping("/home/cookie/increase")
  @ResponseBody
  public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // 최초의 한번은 쿠키가 존재하지 않는다.
    int countInCookie = 0;

    if(req.getCookies() != null) {
      countInCookie = Arrays.stream(req.getCookies()) // 쿠키 배열을 스트림으로 변환
          .filter(cookie -> cookie.getName().equals("count")) // 쿠키 이름이 "count"인 쿠키만 필터링
          .map(cookie -> cookie.getValue()) // 쿠키의 값을 가져옴
          .mapToInt(Integer::parseInt) // 가져온 쿠키값(문자열)을 정수로 형 변환
          .findFirst() // 필터링 된 쿠키 중에 첫 번째거를 찾음
          .orElse(0); // 만약 찾지 못하면 기본값 0을 반환
    }

    int newCountInCookie = countInCookie + 1;

    resp.addCookie(new Cookie("count", newCountInCookie + ""));

    return newCountInCookie;
  }

  @GetMapping("/home/reqAndResp")
  @ResponseBody
  public void showReqAndResp(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int age = Integer.parseInt(req.getParameter("age"));
    resp.getWriter().append("I'm %d years old\n".formatted(age));
  }
}

class Member1 {
  private String name;
  private int age;
  private String address;
  private String phone;
  private String email;
  private List<String> hobbies;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getHobbies() {
    return hobbies;
  }

  public void setHobbies(List<String> hobbies) {
    this.hobbies = hobbies;
  }

  public Member1(String name, int age, String address, String phone, String email, List<String> hobbies) {
    this.name = name;
    this.age = age;
    this.address = address;
    this.phone = phone;
    this.email = email;
    this.hobbies = hobbies;
  }

  @Override
  public String toString() {
    return "Member{" +
        "name='" + name + '\'' +
        ", age=" + age +
        ", address='" + address + '\'' +
        ", phone='" + phone + '\'' +
        ", email='" + email + '\'' +
        ", hobbies=" + hobbies +
        '}';
  }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Member2 {
  private String name;
  private int age;
  private String address;
  private String phone;
  private String email;
  private List<String> hobbies;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Person {
  private static int lastId;
  private int id;
  private int age;
  private String name;

  static {
    lastId = 0;
  }

  public Person(int age, String name) {
    this(++lastId, age, name);
  }
}