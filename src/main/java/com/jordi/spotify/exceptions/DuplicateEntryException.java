package com.jordi.spotify.exceptions;

import com.jordi.spotify.dto.ErrorDto;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class DuplicateEntryException extends SpotifyException {
    private static final long serialVersionUID = -5875250972290252821L;

    public DuplicateEntryException(final String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }

    public DuplicateEntryException(final String message, final ErrorDto data) {
        super(HttpStatus.CONFLICT.value(), message, Arrays.asList(data));
    }
}
