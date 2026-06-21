package com.mattrack.weighthistory;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WeightHistoryNotFoundException extends RuntimeException {

    public WeightHistoryNotFoundException() {
        super("Weight history not found");
    }
}