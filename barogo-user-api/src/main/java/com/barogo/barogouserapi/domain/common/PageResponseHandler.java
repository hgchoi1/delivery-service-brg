package com.barogo.barogouserapi.domain.common;

import java.util.List;
import org.springframework.data.domain.Page;

public class PageResponseHandler {

  public static PageResponse of(Page<?> page) {
    int pageNumber = page.getPageable().getPageNumber() + 1;
    int pageSize = page.getPageable().getPageSize();
    boolean isFirstPage = page.isFirst();
    boolean isLastPage = page.isLast();
    int totalPages = page.getTotalPages();
    long totalElements = page.getTotalElements();
    List<?> content = page.getContent();

    return new PageResponse(pageNumber, pageSize, isFirstPage, isLastPage, totalPages,
        totalElements, content);
  }

}

