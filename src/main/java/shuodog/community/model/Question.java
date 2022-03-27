package shuodog.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private Integer creator;
    private Integer readCount;
    private Integer commentCount;
    private Integer likeCount;
    private Integer unlikeCount;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
}
