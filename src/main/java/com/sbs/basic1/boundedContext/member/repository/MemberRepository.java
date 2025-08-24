package com.sbs.basic1.boundedContext.member.repository;

import com.sbs.basic1.boundedContext.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // 임시방편
public class MemberRepository {
  private List<Member> members;

  public MemberRepository() {
    members = new ArrayList<>();

    // 테스트 회원 10명 생성
    members.add(new Member("user1", "1234"));
    members.add(new Member("user2", "1234"));
    members.add(new Member("user3", "1234"));
    members.add(new Member("abcd", "123456"));
    members.add(new Member("test", "12347"));
    members.add(new Member("love", "12348"));
    members.add(new Member("goog", "12349"));
    members.add(new Member("choice", "123456"));
    members.add(new Member("spring", "123456"));
    members.add(new Member("summer", "123456"));
  }

  public Member findByUsername(String username) {
    return members.stream()
        .filter(member -> member.getUsername().equals(username))
        .findFirst()
        .orElse(null);
  }

  public Member findById(long id) {
    return members.stream()
        .filter(member -> member.getId() == id)
        .findFirst()
        .orElse(null);
  }
}
