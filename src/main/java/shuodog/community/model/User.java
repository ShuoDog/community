package shuodog.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String avatarUrl;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
}
