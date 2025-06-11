package com.damian34.notes.api.protocol.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Response object representing a note")
public record NoteResponse(
    @Schema(description = "Unique identifier of the note")
    UUID id,
    @Schema(description = "Name of the note")
    String name,
    @Schema(description = "Content of the note")
    String content
) {
}