package com.damian34.notes.domain.dto;

import java.util.UUID;

public record NoteFilterDto(
        UUID id,
        String name,
        String content
) {
}
