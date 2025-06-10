package com.damian34.notes.api.protocol.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for creating a new note")
public record CreateNoteRequest(
    @Schema(description = "Name of the note")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be max 100 characters")
    String name,

    @Schema(description = "Content of the note")
    String content
) {
}