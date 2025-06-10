package com.damian34.notes.domain.model;

import java.util.List;

public record Paged<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements
) {
}
