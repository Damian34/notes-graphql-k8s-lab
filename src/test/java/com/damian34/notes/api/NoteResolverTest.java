package com.damian34.notes.api;

import com.damian34.notes.domain.dto.CreateNoteDto;
import com.damian34.notes.domain.service.NoteFacade;
import com.damian34.notes.api.protocol.request.CreateNoteRequest;
import com.damian34.notes.api.protocol.request.UpdateNoteRequest;
import com.damian34.notes.api.protocol.request.NoteFilterRequest;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.infrastructure.db.NoteJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("squid:S2699")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteResolverTest {

    @Autowired
    private NoteFacade noteFacade;

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NoteJpaRepository noteJpaRepository;

    @BeforeEach
    void setUp() {
        noteJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create new note when valid request is provided")
    void shouldCreateNoteWhenValidRequest() {
        // given
        CreateNoteRequest request = new CreateNoteRequest("Test Note", "Test Content");

        // when & then
        graphQlTester.document(GraphQLNoteQueries.CREATE_NOTE)
                .variable("request", mapper.convertValue(request, Map.class))
                .execute()
                .path("data.createNote.id").entity(String.class).satisfies(Assertions::assertNotNull)
                .path("data.createNote.name").entity(String.class).isEqualTo(request.name())
                .path("data.createNote.content").entity(String.class).isEqualTo(request.content());
    }

    @Test
    @DisplayName("Should throw when creating note with empty name")
    void shouldThrowWhenCreatingNoteWithEmptyName() {
        // given
        CreateNoteRequest request = new CreateNoteRequest("", "Test Content");

        // when & then
        graphQlTester.document(GraphQLNoteQueries.CREATE_NOTE)
                .variable("request", mapper.convertValue(request, Map.class))
                .execute()
                .errors()
                .satisfy(errors -> {
                    assert errors.stream().anyMatch(e -> "Name cannot be empty".equals(e.getMessage()));
                });
    }

    @Test
    @DisplayName("Should return paginated notes when filter")
    void shouldReturnPaginatedNotesWhenFilter() {
        // given
        Note note = noteFacade.createNote(new CreateNoteDto("Test Note", "Test Content"));
        NoteFilterRequest filter = new NoteFilterRequest(null, "Test", null);
        int page = 0;
        int size = 10;

        // when & then
        graphQlTester.document(GraphQLNoteQueries.READ_NOTES)
                .variable("page", page)
                .variable("size", size)
                .variable("filter", mapper.convertValue(filter, Map.class))
                .execute()
                .path("data.readNotes.pageNumber").entity(Integer.class).isEqualTo(page)
                .path("data.readNotes.pageSize").entity(Integer.class).isEqualTo(size)
                .path("data.readNotes.totalElements").entity(Long.class).isEqualTo(1L)
                .path("data.readNotes.content[0].id").entity(String.class).satisfies(Assertions::assertNotNull)
                .path("data.readNotes.content[0].name").entity(String.class).isEqualTo(note.name())
                .path("data.readNotes.content[0].content").entity(String.class).isEqualTo(note.content());
    }

    @Test
    @DisplayName("Should return empty list when filter doesn't match any notes")
    void shouldReturnEmptyListWhenFilterDoesNotMatch() {
        // given
        noteFacade.createNote(new CreateNoteDto("Test Note", "Test Content"));
        NoteFilterRequest filter = new NoteFilterRequest(null, "NonExisting", null);
        int page = 0;
        int size = 10;

        // when & then
        graphQlTester.document(GraphQLNoteQueries.READ_NOTES)
                .variable("page", page)
                .variable("size", size)
                .variable("filter", mapper.convertValue(filter, Map.class))
                .execute()
                .path("data.readNotes.totalElements").entity(Long.class).isEqualTo(0L);
    }

    @Test
    @DisplayName("Should update existing note when valid request")
    void shouldUpdateNoteWhenValidRequest() {
        // given
        Note note = noteFacade.createNote(new CreateNoteDto("Test Note", "Test Content"));
        UpdateNoteRequest updateRequest = new UpdateNoteRequest(note.id(), "Updated Note", "Updated Content");

        // when & then
        graphQlTester.document(GraphQLNoteQueries.UPDATE_NOTE)
                .variable("request", mapper.convertValue(updateRequest, Map.class))
                .execute()
                .path("data.updateNote.id").entity(String.class).isEqualTo(note.id().toString())
                .path("data.updateNote.name").entity(String.class).isEqualTo(updateRequest.name())
                .path("data.updateNote.content").entity(String.class).isEqualTo(updateRequest.content());
    }


    @Test
    @DisplayName("Should throw when updating note with non-existing ID")
    void shouldThrowWhenUpdatingNoteWithNonExistingId() {
        // given
        UUID nonExistingId = UUID.randomUUID();
        UpdateNoteRequest request = new UpdateNoteRequest(nonExistingId, "Updated Note", "Updated Content");

        // when & then
        graphQlTester.document(GraphQLNoteQueries.UPDATE_NOTE)
                .variable("request", mapper.convertValue(request, Map.class))
                .execute()
                .errors()
                .satisfy(errors -> {
                    assert errors.stream().anyMatch(e ->
                            Objects.requireNonNull(e.getMessage()).contains("doesn't exist"));
                });
    }

    @Test
    @DisplayName("Should delete note when valid id")
    void shouldDeleteNoteWhenValidId() {
        // given
        Note note = noteFacade.createNote(new CreateNoteDto("Test Note", "Test Content"));

        // when & then
        graphQlTester.document(GraphQLNoteQueries.DELETE_NOTE)
                .variable("id", note.id())
                .execute()
                .path("data.deleteNote").entity(Boolean.class).satisfies(Assertions::assertTrue);
    }

    @Test
    @DisplayName("Should throw when deleting note with non-existing ID")
    void shouldThrowWhenDeletingNoteWithNonExistingId() {
        // given
        UUID nonExistingId = UUID.randomUUID();

        // when & then
        graphQlTester.document(GraphQLNoteQueries.DELETE_NOTE)
                .variable("id", nonExistingId)
                .execute()
                .errors()
                .satisfy(errors -> {
                    assert errors.stream().anyMatch(e ->
                            Objects.requireNonNull(e.getMessage()).contains("doesn't exist"));
                });
    }
}
