package com.mattrack.training;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException() {
        super("Training not found");
    }
}