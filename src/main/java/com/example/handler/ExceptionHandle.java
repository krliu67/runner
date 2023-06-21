package com.example.handler;

import com.example.common.ReturnData;
import com.example.exception.DiyException;
import com.example.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    // @ControllerAdvice 是一个@Component，@ExceptionHandler，@InitBinder和@ModelAttribute等方法需要在其基础上实现，
    // 通过这个注解Spring才能识别到这个类
    // @ExceptionHandler 该注解就是用来告诉Spring这个方法就是用来抛出异常
    // @ResponseBody 作用是以JSON形式返回结果
    // https://blog.csdn.net/qq_29080105/article/details/79169185
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ReturnData handle(Exception e) {
        //如果该异常来自于DiyException，则通过用户传入的code和message抛出
        if (e instanceof DiyException) {
            DiyException diyException = (DiyException) e;
            return ReturnData.error(diyException.getCode(), diyException.getMessage());
        }
        //否则直接抛出指定异常（未知错误）
        else {
            log.info("异常:" + e.toString());
            log.info("报错原因:" +e.getMessage());
            return ReturnData.error(ErrorType.UNKNOWN_ERROR.getCode(), ErrorType.UNKNOWN_ERROR.getMessage());
        }
    }
}
