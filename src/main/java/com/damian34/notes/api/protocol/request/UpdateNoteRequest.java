package com.damian34.notes.api.protocol.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Schema(description = "Request object for updating an existing note")
public record UpdateNoteRequest(
    @Schema(description = "Unique identifier of the note")
    UUID id,
    
    @Schema(description = "Name of the note")
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be max 100 characters")
    String name,
    
    @Schema(description = "Content of the note")
    @NotBlank(message = "Content cannot be empty")
    String content
) {
}