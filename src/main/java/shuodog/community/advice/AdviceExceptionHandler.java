package shuodog.community.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import shuodog.community.exception.ExceptionMessage;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@ControllerAdvice
public class AdviceExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AdviceExceptionHandler.class);
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    ModelAndView modelAndView(Throwable t, Model model,Exception e)
    {
        if(t instanceof ExceptionMessage)
        {
            ExceptionMessage exceptionMessage =(ExceptionMessage) t;
            String message = "错误："+exceptionMessage.getErrorCode()+" "+exceptionMessage.getErrorMessage();
            log.error(getExceptionInfo(e));
            model.addAttribute("message",message);
        }
        else {
            log.error(getExceptionInfo(e));
            model.addAttribute("message","服务器出现故障，请稍后再试！");
        }
        return new ModelAndView("error");
    }


    private static String getExceptionInfo(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        ex.printStackTrace(printStream);
        String string = new String(out.toByteArray());
        try {
            printStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

}
