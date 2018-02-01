package pl.coderslab.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pl.coderslab.exception.BaseEntityNotFoundException;

@ControllerAdvice
public class ErrorControllerAdvaice {
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(BaseEntityNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public String handleException(HttpServletRequest req,Throwable throwable,Exception exception, Model model) {
        model.addAttribute("exception", exception);
        model.addAttribute("url", req.getRequestURL() );
        model.addAttribute("errorMessage", exception.getMessage());
        return "exception-page";
    }
}
