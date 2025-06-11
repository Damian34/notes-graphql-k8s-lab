package com.damian34.notes.domain.exception;

public class NoteException extends RuntimeException {

    public NoteException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NoteException(String message) {
        super(message);
    }
}
