package com.damian34.notes.api.protocol.response;

import java.util.UUID;

public record NoteResponse(
    UUID id,
    String name,
    String content
) {
}