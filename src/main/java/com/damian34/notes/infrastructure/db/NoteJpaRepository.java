package com.damian34.notes.infrastructure.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteJpaRepository extends JpaRepository<NoteEntity, UUID>, JpaSpecificationExecutor<NoteEntity> {
    boolean existsByNameIgnoreCase(String name);
}
