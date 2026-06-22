package com.mattrack.common;

import com.mattrack.technique.TechniqueNotFoundException;
import com.mattrack.technique.TechniqueNotOwnedException;
import com.mattrack.training.TrainingNotFoundException;
import com.mattrack.trainingtechnique.TrainingTechniqueNotFoundException;
import com.mattrack.weighthistory.WeightHistoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Bad Request");
        return problem;
    }

    @ExceptionHandler(TechniqueNotOwnedException.class)
    public ProblemDetail handleTechniqueNotOwned(TechniqueNotOwnedException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        problem.setTitle("Forbidden");
        return problem;
    }

    @ExceptionHandler({
        TrainingNotFoundException.class,
        TechniqueNotFoundException.class,
        WeightHistoryNotFoundException.class,
        TrainingTechniqueNotFoundException.class
    })
    public ProblemDetail handleNotFound(RuntimeException ex) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Not Found");
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        String detail = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
        problem.setTitle("Validation Failed");
        return problem;
    }
}
