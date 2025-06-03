package com.example.unknown.aop;

import com.example.unknown.enums.ResultCode;
import com.example.unknown.domain.ResultVo;
import com.example.unknown.exception.APIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * controller层-统一封装返回异常信息
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler({BindException.class})
    public ResultVo MethodArgumentNotValidExceptionHandler(BindException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return new ResultVo(ResultCode.VALIDATE_ERROR, objectError.getDefaultMessage());
    }

    @ExceptionHandler(APIException.class)
    public ResultVo APIExceptionHandler(APIException e) {
        log.error(e.getMessage(), e);
        return new ResultVo(e.getCode(), e.getMsg(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultVo APIExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new ResultVo(ResultCode.FAILED);
    }
}
