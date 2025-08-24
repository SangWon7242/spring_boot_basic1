package com.sbs.basic1.boundedContext.member.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Member {
  private static long lastId;
  private final long id;
  private final String username;
  private String password;

  static {
    lastId = 0;
  }

  public Member(String username, String password) {
    this(++lastId, username, password);
  }

}
