package com.joe.testdb.exception;

import com.joe.testdb.constants.BootError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ResponseWrapper<Object>> handleException(HttpServletRequest request, Throwable throwable) {
        ResponseEntity<ResponseWrapper<Object>> resp = null;
        if (throwable instanceof BusinessException) {
            log.warn("BusinessException[{}]", throwable.getMessage());
            BusinessException be = (BusinessException) throwable;
            resp = ResponseEntity.status(be.getStatus()).body(ResponseWrapper.fail(be.getCode(),be.getMessage()));
        } else if (throwable instanceof NoHandlerFoundException) {
            log.warn(String.format("NoHandlerFoundException, msg:[%s]", throwable.getMessage()), throwable);
            resp = fail(BootError.NOT_FOUND_URL, request.getRequestURI());
        } else if (throwable instanceof HttpRequestMethodNotSupportedException) {
            log.warn(String.format("HttpRequestMethodNotSupportedException, msg:[%s]", throwable.getMessage()), throwable);
            resp = fail(BootError.METHOD_NOT_ALLOWED, request.getRequestURI());
        } else if (throwable instanceof HttpMessageNotReadableException) {
            log.warn(String.format("HttpMessageNotReadableException, msg:[%s]", throwable.getMessage()), throwable);
            resp = fail(BootError.PKG_FORMAT);
        }else if (throwable instanceof MethodArgumentNotValidException) {
            log.warn(String.format("MethodArgumentNotValidException, msg:[%s]", throwable.getMessage()), throwable);
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) throwable;
            if (exception.getBindingResult().getErrorCount() > 1) {
                resp = ResponseEntity.status(BootError.INVALID_PARAM.status()).
                        body(ResponseWrapper.fail(BootError.INVALID_PARAM.code(), getBindResultMessage(exception.getBindingResult())));
            } else {
                FieldError fieldError = exception.getBindingResult().getFieldError();
                resp = fail(BootError.INVALID_PARAM, fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
//       新的异常在这里添加
//        else if(throwable instanceof DuplicateKeyException){
//            resp = fail(BootError.INVALID_PARAM,)
//        }
        else{
            log.error("Exception occurred:",throwable);
            resp = fail(BootError.UNKNOWN);
        }

        return resp;

    }


    private ResponseEntity<ResponseWrapper<Object>> fail (BootError bootError, Object...args){
        String msg = String.format(bootError.message(), args);
        return ResponseEntity.status(bootError.status()).body(ResponseWrapper.fail(bootError.code(), msg));
    }

    private String getBindResultMessage(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder(":").append(bindingResult.getErrorCount()).append(" error(s): ");
        for (FieldError error : bindingResult.getFieldErrors()) {
            sb.append("[").append(error.getField()).append(": ").append(error.getDefaultMessage()).append("] ");
        }
        return sb.toString();
    }

}
