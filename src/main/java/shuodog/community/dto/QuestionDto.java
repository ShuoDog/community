package shuodog.community.dto;

import lombok.Data;
import shuodog.community.model.User;

@Data
public class QuestionDto {
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
    private User user;
}
