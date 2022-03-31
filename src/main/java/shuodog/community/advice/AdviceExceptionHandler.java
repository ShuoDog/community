package shuodog.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import shuodog.community.exception.ExceptionMessage;

@ControllerAdvice
public class AdviceExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    ModelAndView modelAndView(Throwable e, Model model)
    {
        if(e instanceof ExceptionMessage)
        {
            model.addAttribute("message",e.getMessage());
        }
        else {
            model.addAttribute("message","服务器出现故障，请稍后再试！");
        }

        return new ModelAndView("error");
    }
    
}
