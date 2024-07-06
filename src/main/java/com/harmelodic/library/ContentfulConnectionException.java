package com.harmelodic.library;

public class ContentfulConnectionException extends Exception {
    ContentfulConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
