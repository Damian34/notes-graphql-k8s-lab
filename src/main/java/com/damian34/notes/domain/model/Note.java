package com.damian34.notes.domain.model;

import java.util.UUID;

public record Note(
        UUID id,
        String name,
        String content
) {
}
