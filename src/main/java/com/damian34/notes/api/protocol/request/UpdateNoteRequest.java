package com.damian34.notes.api.protocol.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UpdateNoteRequest(
    UUID id,

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name must be max 100 characters")
    String name,

    @NotBlank(message = "Content cannot be empty")
    String content
) {
}