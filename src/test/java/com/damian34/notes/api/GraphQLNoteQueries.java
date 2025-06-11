package com.damian34.notes.api;

public final class GraphQLNoteQueries {
    private GraphQLNoteQueries() {
        throw new AssertionError("Utility class");
    }

    // language=GraphQL
    public static final String CREATE_NOTE = """
            mutation CreateNote($request: CreateNoteRequest!) {
                createNote(request: $request) {
                    id
                    name
                    content
                }
            }
            """;

    // language=GraphQL
    public static final String READ_NOTES = """
            query ReadNotes($page: Int!, $size: Int!, $filter: NoteFilterRequest) {
                readNotes(page: $page, size: $size, filter: $filter) {
                    content {
                        id
                        name
                        content
                    }
                    pageNumber
                    pageSize
                    totalElements
                }
            }
            """;

    // language=GraphQL
    public static final String UPDATE_NOTE = """
            mutation UpdateNote($request: UpdateNoteRequest!) {
                updateNote(request: $request) {
                    id
                    name
                    content
                }
            }
            """;

    // language=GraphQL
    public static final String DELETE_NOTE = """
            mutation DeleteNote($id: UUID!) {
                deleteNote(id: $id)
            }
            """;
} 