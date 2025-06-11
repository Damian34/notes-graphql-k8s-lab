package com.damian34.notes.infrastructure.service;

import com.damian34.notes.domain.dto.CreateNoteDto;
import com.damian34.notes.domain.dto.UpdateNoteDto;
import com.damian34.notes.domain.exception.NoteException;
import com.damian34.notes.domain.model.Note;
import com.damian34.notes.domain.service.NoteEditor;
import com.damian34.notes.infrastructure.db.NoteEntity;
import com.damian34.notes.infrastructure.db.NoteJpaRepository;
import com.damian34.notes.infrastructure.mapper.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

@RequiredArgsConstructor
public class NoteEditorImpl implements NoteEditor {
    private final NoteJpaRepository noteJpaRepository;
    private final NoteMapper noteMapper;
    private final TransactionTemplate transactionTemplate;

    @Override
    public Note createNote(CreateNoteDto note) {
        NoteEntity entity = noteMapper.toEntity(note);
        NoteEntity savedEntity = noteJpaRepository.save(entity);
        return noteMapper.toDomain(savedEntity);
    }

    @Override
    public Note updateNote(UpdateNoteDto note) {
        return transactionTemplate.execute(status -> {
            NoteEntity entity = noteJpaRepository.findById(note.id())
                    .orElseThrow(() -> new NoteException("Note not found with id: " + note.id()));
            noteMapper.updateEntity(entity, note);
            NoteEntity savedEntity = noteJpaRepository.save(entity);
            return noteMapper.toDomain(savedEntity);
        });
    }

    @Override
    public void deleteNote(UUID id) {
        noteJpaRepository.deleteById(id);
    }
}
