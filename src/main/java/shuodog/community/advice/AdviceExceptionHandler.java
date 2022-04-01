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
            ExceptionMessage exceptionMessage =(ExceptionMessage) e;
            String message = "错误："+exceptionMessage.getErrorCode()+" "+exceptionMessage.getErrorMessage();
            System.out.println("错误："+exceptionMessage.getErrorCode()+"\n"+exceptionMessage.getErrorMessage());
            model.addAttribute("message",message);
        }
        else {
            model.addAttribute("message","服务器出现故障，请稍后再试！");
        }
        return new ModelAndView("error");
    }
    
}
