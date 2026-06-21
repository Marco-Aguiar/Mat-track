package com.mattrack.trainingtechnique;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainingTechniqueNotFoundException extends RuntimeException {

    public TrainingTechniqueNotFoundException() {
        super("Technique is not linked to this training");
    }
}