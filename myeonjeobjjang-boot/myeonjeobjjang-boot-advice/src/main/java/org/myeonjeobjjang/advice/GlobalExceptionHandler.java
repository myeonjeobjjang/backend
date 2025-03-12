package org.myeonjeobjjang.advice;

import lombok.extern.slf4j.Slf4j;
import org.myeonjeobjjang.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseException e) {
        log.debug(printExceptionLog(e));
        return new ResponseEntity<>(new ExceptionResponse(e.getErrorCode(), e.getMessage()), e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleNotValidArgument(MethodArgumentNotValidException e) {
        log.debug(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(new ExceptionResponse("INVALID_ARGUMENT", e.getBindingResult().getAllErrors().get(0).getDefaultMessage()), e.getStatusCode());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        log.error(printExceptionLog(e));
        return new ResponseEntity<>(new ExceptionResponse("UNHANDLED", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String printExceptionLog(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e);
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append("\n\t at ")
                .append(ste.toString());
        }
        if (e.getCause() != null) {
            sb.append("\nCaused by: ").append(e.getCause())
                .append("\n\t... ").append(e.getCause().getStackTrace().length).append(" more");
        }
        return sb.toString();
    }
}
