package com.example.instagram.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFound(NoResourceFoundException ex) {
        return "error/404";
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(
            BusinessException ex,
            Model model
    ) {
        ErrorCode errorCode = ex.getErrorCode();

        model.addAttribute("message", errorCode.getMessage());

        return switch(errorCode.getHttpStatus()) {
            case NOT_FOUND -> "error/404";
            case BAD_REQUEST -> "error/400";
            default -> "error/500";
        };
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceeded(
            MaxUploadSizeExceededException ex,
            Model model
    ) {
        model.addAttribute("message", "파일 크기가 10MB를 초과했습니다.");
        return "error/400";
    }

    //모든 예외 처리
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/500";
    }

}
