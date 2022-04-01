package shuodog.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shuodog.community.exception.EnumExceptionImplements;
import shuodog.community.exception.ExceptionMessage;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/error")
public class ExceptionController implements ErrorController {

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public void errorPathHandler(HttpServletResponse response){
        EnumExceptionImplements enumExceptionImplements = null;
        switch (response.getStatus())
        {
            case 200:
                enumExceptionImplements=EnumExceptionImplements.SUCCESS;
                break;
            case 400:
                enumExceptionImplements=EnumExceptionImplements.BODY_NOT_MATCH;
                break;
            case 401:
                enumExceptionImplements=EnumExceptionImplements.SIGNATURE_NOT_MATCH;
                break;
            case 404:
                enumExceptionImplements=EnumExceptionImplements.NOT_FOUND;
                break;
            case 500:
                enumExceptionImplements=EnumExceptionImplements.INTERNAL_SERVER_ERROR;
                break;
            case 503:
                enumExceptionImplements=EnumExceptionImplements.SERVER_BUSY;
                break;
        }
        throw new ExceptionMessage(enumExceptionImplements);

    }

}
