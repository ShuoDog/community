package shuodog.community.exception;

public class ExceptionMessage extends RuntimeException{

    private Integer errorCode;
    private String errorMessage;

    public ExceptionMessage(EnumExceptionInterface enumExceptionInterface)
    {

        this.errorCode=enumExceptionInterface.getStateCode();
        this.errorMessage=enumExceptionInterface.getStateMessage();
    }

    public ExceptionMessage(Integer errorCode,String errorMessage){

        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
