package com.damian34.notes.api.protocol.request;

public record NoteFilterRequest(
    String id,
    String name,
    String content
) {
}