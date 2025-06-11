package com.damian34.notes.domain.service;

import com.damian34.notes.domain.dto.CreateNoteDto;
import com.damian34.notes.domain.dto.UpdateNoteDto;
import com.damian34.notes.domain.model.Note;

import java.util.UUID;

public interface NoteEditor {
    Note createNote(CreateNoteDto note);

    Note updateNote(UpdateNoteDto note);

    void deleteNote(UUID id);
}
