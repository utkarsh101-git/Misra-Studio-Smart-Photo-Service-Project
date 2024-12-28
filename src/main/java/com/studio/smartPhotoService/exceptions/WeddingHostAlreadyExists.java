package com.studio.smartPhotoService.exceptions;

/*
Runtime Exception raised when already a Host exists with the same email Id
 */
public class WeddingHostAlreadyExists extends RuntimeException {
    public WeddingHostAlreadyExists(String s) {
        super(s);
    }
}
