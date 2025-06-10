package com.damian34.notes.domain.service;

import com.damian34.notes.domain.dto.CreateNoteDto;
import com.damian34.notes.domain.dto.UpdateNoteDto;
import com.damian34.notes.domain.dto.NoteFilterDto;
import com.damian34.notes.domain.exception.NoteException;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class NoteFacade {
    private final NoteEditor noteEditor;
    private final NoteQuerySearcher noteQuerySearcher;
    private final NoteValidationPolicy noteValidation;
    
    public Note createNote(CreateNoteDto note) {
        if(noteValidation.existsNoteByName(note.name())) {
            throw new NoteException("Note with name '" + note.name() + "' already exists");
        }
        return noteEditor.createNote(note);
    }

    public Paged<Note> getNotes(NoteFilterDto filter, int page, int size) {
        return noteQuerySearcher.getNotes(filter, page, size);
    }

    public Note updateNote(UpdateNoteDto note) {
        if(note.id() == null || noteValidation.notExistsNoteById(note.id())) {
            throw new NoteException("Note with ID '" + note.id() + "' doesn't exist");
        }
        return noteEditor.updateNote(note);
    }

    public void deleteNote(UUID id) {
        if(noteValidation.notExistsNoteById(id)) {
            throw new NoteException("Note with ID '" + id + "' doesn't exist");
        }
        noteEditor.deleteNote(id);
    }
}
