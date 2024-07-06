package com.harmelodic.library;

public class FailedToFetchLibraryException extends Exception {
    public FailedToFetchLibraryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
