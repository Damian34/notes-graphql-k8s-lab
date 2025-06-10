package com.damian34.notes.infrastructure.service;

import com.damian34.notes.domain.service.NoteValidationPolicy;
import com.damian34.notes.infrastructure.db.NoteJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class NoteValidationPolicyImpl implements NoteValidationPolicy {
    private final NoteJpaRepository noteJpaRepository;

    @Override
    public boolean existsNoteByName(String name) {
        return noteJpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean notExistsNoteById(UUID id) {
        return !noteJpaRepository.existsById(id);
    }
}
