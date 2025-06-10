package com.damian34.notes.infrastructure.mapper;

import com.damian34.notes.domain.dto.CreateNoteDto;
import com.damian34.notes.domain.dto.UpdateNoteDto;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;
import com.damian34.notes.infrastructure.db.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    @Mapping(target = "id", ignore = true)
    NoteEntity toEntity(CreateNoteDto dto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget NoteEntity entity, UpdateNoteDto dto);

    Note toDomain(NoteEntity entity);
    
    @Mapping(source = "content", target = "content")
    @Mapping(source = "totalElements", target = "totalElements")
    @Mapping(source = "number", target = "pageNumber")
    @Mapping(source = "size", target = "pageSize")
    Paged<Note> toPaged(Page<NoteEntity> page);
}
