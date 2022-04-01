package shuodog.community.exception;

public enum EnumExceptionImplements implements EnumExceptionInterface {

    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    NOT_FOUND("404", "未找到该资源!"),
    QUESTION_NOT_FOUND("404","你找的问题不存在或者已经被删除"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVER_BUSY("503","服务器正忙，请稍后再试!");

    private String stateCode;
    private String stateMessage;

    EnumExceptionImplements(String stateCode, String stateMessage) {
        this.stateCode = stateCode;
        this.stateMessage = stateMessage;
    }

    public String getStateCode() {return stateCode;}

    public String getStateMessage() {
        return stateMessage;
    }
}
