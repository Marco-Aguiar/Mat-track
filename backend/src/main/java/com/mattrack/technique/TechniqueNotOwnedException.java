package com.mattrack.technique;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TechniqueNotOwnedException extends RuntimeException {

    public TechniqueNotOwnedException() {
        super("You can only modify techniques you created");
    }
}
