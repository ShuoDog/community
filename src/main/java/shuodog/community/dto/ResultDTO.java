package shuodog.community.dto;

import lombok.Data;
import shuodog.community.exception.EnumExceptionImplements;
import shuodog.community.exception.ExceptionMessage;

@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO failRequest(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO failRequest(ExceptionMessage exceptionMessage) {
        return failRequest(exceptionMessage.getErrorCode(),exceptionMessage.getErrorMessage());
    }

    public static ResultDTO failRequest(EnumExceptionImplements enumExceptionImplements){
        return failRequest(enumExceptionImplements.getStateCode(),enumExceptionImplements.getStateMessage());
    }

    public static ResultDTO successRequest(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }


}
