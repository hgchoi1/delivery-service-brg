package com.barogo.barogouserapi.domain.common;

import static java.util.Objects.requireNonNullElse;

import org.springframework.data.domain.Sort.Direction;

public record PageRequest(Integer page, Integer size) {
  public PageRequest {
    page = requireNonNullElse(page, 1);
    size = requireNonNullElse(size, 100);
  }
  public org.springframework.data.domain.PageRequest of() {
    return org.springframework.data.domain.PageRequest.of(page - 1, size, Direction.DESC, "userSeq");
  }
}