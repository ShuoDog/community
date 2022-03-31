package shuodog.community.exception;

public class ExceptionMessage extends RuntimeException{

    private String message;

    public ExceptionMessage(String message)
    {
        this.message=message;
    }

    public ExceptionMessage(EnumExceptionInterface enumExceptionInterface)
    {
        this.message=enumExceptionInterface.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
