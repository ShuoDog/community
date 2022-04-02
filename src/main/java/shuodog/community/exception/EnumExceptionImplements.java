package shuodog.community.exception;

public enum EnumExceptionImplements implements EnumExceptionInterface {

    SUCCESS(200, "成功!"),
    BODY_NOT_MATCH(400,"请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401,"请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503,"服务器正忙，请稍后再试!"),

    TYPE_NOT_FOUND(1997,"评论类型不存在"),
    USER_NOT_FOUND(1998,"用户未登录请重新登录"),
    QUESTION_NOT_FOUND(1999,"问题不存在或者已经被删除"),
    COMMENT_NOT_FOUND(2000,"评论不存在或者已经被删除");

    private final Integer stateCode;
    private final String stateMessage;

    public Integer getStateCode(){return stateCode;}

    public String getStateMessage() {
        return stateMessage;
    }

    EnumExceptionImplements(Integer stateCode, String stateMessage) {
        this.stateCode = stateCode;
        this.stateMessage = stateMessage;
    }

}
