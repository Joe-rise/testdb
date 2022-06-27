package com.joe.testdb.exception;

import com.joe.testdb.constants.BootError;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BusinessException extends Exception {

    private String msg;
    private Object data;
    private int code;
    private int status = HttpStatus.OK.value();

    public BusinessException(BootError bootError, Object... args) {
        this.msg = String.format(bootError.message(), args);
        this.code = bootError.code();
        this.status = bootError.status();
    }

    public BusinessException(int code, String msg, Object... args) {
        this.code = code;
        this.msg = String.format(msg, args);
    }

    public BusinessException(Object data, BootError bootError, Object... args) {
        this.data = data;
        this.msg = String.format(bootError.message(), args);
        this.code = bootError.code();
        this.status = bootError.status();
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
