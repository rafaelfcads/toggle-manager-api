package com.it.toggle.domain.toggle;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such Toggle")
public class ToggleNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 2160616847804811038L;
	
    public ToggleNotFoundException() {
        super();
    }

    public ToggleNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ToggleNotFoundException(final String message) {
        super(message);
    }

    public ToggleNotFoundException(final Throwable cause) {
        super(cause);
    }
	
}
