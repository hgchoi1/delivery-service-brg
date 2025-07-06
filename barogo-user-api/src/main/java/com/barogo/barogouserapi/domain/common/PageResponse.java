package com.barogo.barogouserapi.domain.common;

import java.util.List;

public record PageResponse(
    Integer page,
    Integer size,
    Boolean first,
    Boolean last,
    Integer totalPage,
    Long totalElement,
    List<?> content
) {

}