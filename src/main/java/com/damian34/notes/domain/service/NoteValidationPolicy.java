package com.damian34.notes.domain.service;

import java.util.UUID;

public interface NoteValidationPolicy {
    boolean existsNoteByName(String name);

    boolean notExistsNoteById(UUID id);
}
