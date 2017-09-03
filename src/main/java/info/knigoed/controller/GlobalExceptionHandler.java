package info.knigoed.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleIOException(Exception e) {
        LOG.info("", e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", e);
        modelAndView.setViewName("bundles/error/error");
        return modelAndView;
    }


}
