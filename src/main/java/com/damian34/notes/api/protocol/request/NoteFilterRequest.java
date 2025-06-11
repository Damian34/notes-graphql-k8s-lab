package com.damian34.notes.api.protocol.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object for filtering notes")
public record NoteFilterRequest(
    @Schema(description = "Note ID")
    String id,
    @Schema(description = "Note name")
    String name,
    @Schema(description = "Note content")
    String content
) {
}