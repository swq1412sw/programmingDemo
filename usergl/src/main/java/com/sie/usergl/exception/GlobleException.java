package com.sie.usergl.exception;



import com.sie.usergl.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@Slf4j
@RestControllerAdvice
public class GlobleException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result exception(RuntimeException runtimeException){
        log.error("系统运行异常--------->",runtimeException.getMessage());

        return Result.fail(runtimeException.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = AuthenticationException.class)
    public Result exception(AuthenticationException e){
        log.error("权限DSADAS足",e.getMessage());

        return Result.fail(e.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result exception(AccessDeniedException e){

        log.error("权限不足，请联系管理员");

        return Result.fail("权限不足，请联系管理员");
    }



}
