package shuodog.community.exception;

public class ExceptionMessage extends RuntimeException{

    private String errorCode;
    private String errorMessage;

    public ExceptionMessage(EnumExceptionInterface enumExceptionInterface)
    {
        super(enumExceptionInterface.getStateCode());
        this.errorCode=enumExceptionInterface.getStateCode();
        this.errorMessage=enumExceptionInterface.getStateMessage();
    }

    public ExceptionMessage(String errorCode,String errorMessage){
        super(errorCode);
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

}
