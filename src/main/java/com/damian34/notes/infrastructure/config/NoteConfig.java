package com.damian34.notes.infrastructure.config;

import com.damian34.notes.domain.service.NoteEditor;
import com.damian34.notes.domain.service.NoteFacade;
import com.damian34.notes.domain.service.NoteQuerySearcher;
import com.damian34.notes.domain.service.NoteValidationPolicy;
import com.damian34.notes.infrastructure.db.NoteJpaRepository;
import com.damian34.notes.infrastructure.mapper.NoteMapper;
import com.damian34.notes.infrastructure.service.NoteEditorImpl;
import com.damian34.notes.infrastructure.service.NoteQuerySearcherImpl;
import com.damian34.notes.infrastructure.service.NoteValidationPolicyImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class NoteConfig {

    @Bean
    NoteFacade noteFacade(NoteJpaRepository noteRepository, NoteMapper noteMapper, TransactionTemplate transactionTemplate) {
        NoteEditor noteEditor = new NoteEditorImpl(noteRepository, noteMapper, transactionTemplate);
        NoteQuerySearcher noteQuerySearcher = new NoteQuerySearcherImpl(noteRepository, noteMapper);
        NoteValidationPolicy noteValidation = new NoteValidationPolicyImpl(noteRepository);
        return new NoteFacade(noteEditor, noteQuerySearcher, noteValidation);
    }
}
