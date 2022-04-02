package shuodog.community.advice;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import shuodog.community.dto.ResultDTO;
import shuodog.community.exception.EnumExceptionImplements;
import shuodog.community.exception.ExceptionMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

@ControllerAdvice
public class AdviceExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(AdviceExceptionHandler.class);
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    Object modelAndView(HttpServletRequest request, HttpServletResponse response,
                        Throwable throwable, Exception exception, Model model)
    {
        String contentType = request.getContentType();
        if("application/json".equals(contentType))
        {
            ResultDTO resultDTO = new ResultDTO();
            if(throwable instanceof ExceptionMessage)
            {
                resultDTO=ResultDTO.failRequest((ExceptionMessage) throwable);
            }
            else {
                log.error(getExceptionInfo(exception));
                resultDTO=ResultDTO.failRequest(EnumExceptionImplements.INTERNAL_SERVER_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        else {
            if(throwable instanceof ExceptionMessage)
            {
                ExceptionMessage exceptionMessage =(ExceptionMessage) throwable;
                String message = "错误："+exceptionMessage.getErrorCode()+" "+exceptionMessage.getErrorMessage();
                log.error(getExceptionInfo(exception));
                model.addAttribute("message",message);
            }
            else {
                log.error(getExceptionInfo(exception));
                model.addAttribute("message","服务器出现故障，请稍后再试！");
            }
            return new ModelAndView("error");
        }

    }


    private static String getExceptionInfo(Exception exception) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(out);
        exception.printStackTrace(printStream);
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
