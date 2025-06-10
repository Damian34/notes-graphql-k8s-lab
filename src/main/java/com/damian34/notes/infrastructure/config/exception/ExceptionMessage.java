package com.damian34.notes.infrastructure.config.exception;

import java.time.LocalDateTime;

record ExceptionMessage(
        LocalDateTime timestamp,
        String message
) {
} 