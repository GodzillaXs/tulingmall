package com.tulingxueyuan.mall.common.exception;


import cn.hutool.core.exceptions.ExceptionUtil;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * @param e
     *  处理自定义的ApiException异常
     * @Date 2021/11/27 15:20
     * @return com.tulingxueyuan.mall.common.api.CommonResult
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * @param e
     *  处理运行时的未知异常
     * @Date 2021/11/27 15:20
     * @return com.tulingxueyuan.mall.common.api.CommonResult
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult handleRuntimeException(RuntimeException e) {
        log.error("运行时发生的未知异常",e);
        return CommonResult.failed(ResultCode.UNKNOWN);
    }

    /**
     * @param e
     *  处理jsr303验证数据时出现的异常
     * @Date 2021/11/27 15:21
     * @return com.tulingxueyuan.mall.common.api.CommonResult
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }
    /**
     * @param e
     *  处理数据绑定的异常
     * @Date 2021/11/27 15:21
     * @return com.tulingxueyuan.mall.common.api.CommonResult
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }
}
