package com.mattrack.technique;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TechniqueNotFoundException extends RuntimeException {

    public TechniqueNotFoundException() {
        super("Technique not found");
    }
}