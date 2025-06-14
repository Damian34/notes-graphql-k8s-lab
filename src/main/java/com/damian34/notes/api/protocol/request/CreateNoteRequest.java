package com.damian34.notes.api.protocol.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNoteRequest(
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be max 100 characters")
    String name,
    String content
) {
}