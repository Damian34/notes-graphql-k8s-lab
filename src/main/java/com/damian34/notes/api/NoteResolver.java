package com.damian34.notes.api;

import com.damian34.notes.api.mapper.NoteRequestMapper;
import com.damian34.notes.api.protocol.request.CreateNoteRequest;
import com.damian34.notes.api.protocol.request.UpdateNoteRequest;
import com.damian34.notes.api.protocol.request.NoteFilterRequest;
import com.damian34.notes.api.protocol.response.NoteResponse;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;
import com.damian34.notes.domain.service.NoteFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Tag(name = "Note GraphQL API", description = "GraphQL API for managing notes")
class NoteResolver {
    private final NoteFacade noteFacade;
    private final NoteRequestMapper noteRequestMapper;

    @MutationMapping
    @Operation(summary = "Create a new note")
    NoteResponse createNote(@Valid @Argument CreateNoteRequest request) {
        Note note = noteFacade.createNote(noteRequestMapper.toDto(request));
        return noteRequestMapper.toResponse(note);
    }

    @QueryMapping
    @Operation(summary = "Get all notes with optional filtering and pagination")
    Paged<NoteResponse> readNotes(
            @Argument int page,
            @Argument int size,
            @Argument NoteFilterRequest filter) {
        Paged<Note> notes = noteFacade.getNotes(noteRequestMapper.toDto(filter), page, size);
        return noteRequestMapper.toPagedResponse(notes);
    }

    @MutationMapping
    @Operation(summary = "Update an existing note")
    NoteResponse updateNote(@Valid @Argument UpdateNoteRequest request) {
        Note note = noteFacade.updateNote(noteRequestMapper.toDto(request));
        return noteRequestMapper.toResponse(note);
    }

    @MutationMapping
    @Operation(summary = "Delete a note by ID")
    Boolean deleteNote(@Argument UUID id) {
        noteFacade.deleteNote(id);
        return true;
    }
}
