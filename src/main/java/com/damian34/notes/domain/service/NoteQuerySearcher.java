package com.damian34.notes.domain.service;

import com.damian34.notes.domain.dto.NoteFilterDto;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;

public interface NoteQuerySearcher {
    Paged<Note> getNotes(NoteFilterDto filterDto, int page, int size);
}
