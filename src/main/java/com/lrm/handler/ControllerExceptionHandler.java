package com.lrm.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice           //以下部分代码为AI辅助生成：DeepSeek, 2026-2-27
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);

        //博客不存在时
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }


        // 构建完整的调试信息注释字符串
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append("<!--\n");
        debugInfo.append("Failed Request URL : ").append(request.getRequestURL()).append("\n");
        debugInfo.append("Exception message : ").append(e.getMessage()).append("\n");

        // 获取异常堆栈
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        debugInfo.append(sw).append("\n");
        debugInfo.append("-->");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("stackTrace", sw.toString());
        modelAndView.addObject("debugComment", debugInfo.toString());

        modelAndView.setViewName("error/error");
        return modelAndView;
    }
}