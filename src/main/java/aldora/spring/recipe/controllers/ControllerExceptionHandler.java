package aldora.spring.recipe.controllers;

import aldora.spring.recipe.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(Exception exception, Model model) {
        log.error("Handling not found exception");

        model.addAttribute("exception", exception);

        return "errors/404";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NumberFormatException.class, WebExchangeBindException.class})
    public String handleRuntimeException(Exception exception, Model model) {
        log.error("Handling bad request exception");
        log.error("excepiton: ", exception);

        model.addAttribute("exception", exception);

        return "errors/400";
    }
}
