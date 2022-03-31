package shuodog.community.exception;

public enum EnumExceptionImplements implements EnumExceptionInterface {

    QUESTION_NOT_FOUND("你找的问题不在了");

    private String message;

    public String getMessage()
    {
        return message;
    }

    EnumExceptionImplements(String message)
    {
        this.message=message;
    }



}
