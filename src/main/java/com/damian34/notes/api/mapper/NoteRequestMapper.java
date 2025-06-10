package com.damian34.notes.api.mapper;

import com.damian34.notes.domain.dto.CreateNoteDto;
import com.damian34.notes.domain.dto.UpdateNoteDto;
import com.damian34.notes.domain.dto.NoteFilterDto;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;
import com.damian34.notes.api.protocol.request.CreateNoteRequest;
import com.damian34.notes.api.protocol.request.UpdateNoteRequest;
import com.damian34.notes.api.protocol.request.NoteFilterRequest;
import com.damian34.notes.api.protocol.response.NoteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteRequestMapper {
    
    CreateNoteDto toDto(CreateNoteRequest request);
    
    UpdateNoteDto toDto(UpdateNoteRequest request);
    
    @Mapping(target = "id", source = "id", qualifiedByName = "mapToUuid")
    NoteFilterDto toDto(NoteFilterRequest request);
    
    NoteResponse toResponse(Note note);

    @Named("toResponseList")
    List<NoteResponse> toResponseList(List<Note> notes);
    
    @Mapping(target = "content", source = "content", qualifiedByName = "toResponseList")
    @Mapping(target = "pageNumber", source = "pageNumber")
    @Mapping(target = "pageSize", source = "pageSize")
    @Mapping(target = "totalElements", source = "totalElements")
    Paged<NoteResponse> toPagedResponse(Paged<Note> paged);

    @Named("mapToUuid")
    default UUID mapToUuid(String id) {
        return id != null ? UUID.fromString(id) : null;
    }
}