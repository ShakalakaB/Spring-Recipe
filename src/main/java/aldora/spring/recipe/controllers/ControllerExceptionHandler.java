package aldora.spring.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView handleNotFound(Exception exception) {
//        log.error("Handling not found exception");
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("errors/404");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberFormatException.class)
//    public ModelAndView handleRuntimeException(Exception exception) {
//        log.error("Handling bad request exception");
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("errors/404");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }
}
