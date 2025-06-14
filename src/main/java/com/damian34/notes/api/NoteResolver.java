package com.damian34.notes.api;

import com.damian34.notes.api.mapper.NoteRequestMapper;
import com.damian34.notes.api.protocol.request.CreateNoteRequest;
import com.damian34.notes.api.protocol.request.UpdateNoteRequest;
import com.damian34.notes.api.protocol.request.NoteFilterRequest;
import com.damian34.notes.api.protocol.response.NoteResponse;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;
import com.damian34.notes.domain.service.NoteFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
class NoteResolver {
    private final NoteFacade noteFacade;
    private final NoteRequestMapper noteRequestMapper;

    @MutationMapping
    NoteResponse createNote(@Valid @Argument CreateNoteRequest request) {
        Note note = noteFacade.createNote(noteRequestMapper.toDto(request));
        return noteRequestMapper.toResponse(note);
    }

    @QueryMapping
    Paged<NoteResponse> readNotes(
            @Argument int page,
            @Argument int size,
            @Argument NoteFilterRequest filter) {
        Paged<Note> notes = noteFacade.getNotes(noteRequestMapper.toDto(filter), page, size);
        return noteRequestMapper.toPagedResponse(notes);
    }

    @MutationMapping
    NoteResponse updateNote(@Valid @Argument UpdateNoteRequest request) {
        Note note = noteFacade.updateNote(noteRequestMapper.toDto(request));
        return noteRequestMapper.toResponse(note);
    }

    @MutationMapping
    Boolean deleteNote(@Argument UUID id) {
        noteFacade.deleteNote(id);
        return true;
    }
}
