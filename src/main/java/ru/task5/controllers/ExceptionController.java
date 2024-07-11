package ru.task5.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;


@ControllerAdvice
public class ExceptionController {
//    Для статуса 500 возвращается стек вызываемых процедур/функций + Текст (Ошибки)
//    Для статусов 400 и 404 возвращается Текст (Ошибки)

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        String message = "";
        for (Object str : Objects.requireNonNull(e.getDetailMessageArguments())) {
            message = str.toString();
        }
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleException(NullPointerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(RuntimeException e) {
        String message = "ERROR: " + e.getMessage() + "\n";
        for (StackTraceElement s : e.getStackTrace()) {
            if (s.getClassName().startsWith("ru.task5")) {
                message += s.getClassName() + " method: " + s.getMethodName() + "\n";
            }
        }
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
