package com.damian34.notes.infrastructure.service;

import com.damian34.notes.domain.dto.NoteFilterDto;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.model.Paged;
import com.damian34.notes.domain.service.NoteQuerySearcher;
import com.damian34.notes.infrastructure.db.NoteEntity;
import com.damian34.notes.infrastructure.db.NoteJpaRepository;
import com.damian34.notes.infrastructure.mapper.NoteMapper;
import com.damian34.notes.infrastructure.service.specification.NoteFilterSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class NoteQuerySearcherImpl implements NoteQuerySearcher {
    private final NoteJpaRepository noteJpaRepository;
    private final NoteMapper noteMapper;

    @Override
    public Paged<Note> getNotes(NoteFilterDto filterDto, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Specification<NoteEntity> specification = NoteFilterSpecification.filter(filterDto);
        Page<NoteEntity> pageResult = noteJpaRepository.findAll(specification, pageRequest);
        return noteMapper.toPaged(pageResult);
    }
}
