package shuodog.community.dto;

import lombok.Data;
import shuodog.community.model.User;

@Data
public class CommentDisplayDTO {
    private Integer id;
    private Integer commentator;
    private Integer parentId;
    private String content;
    private Integer type;
    private Integer like_count;
    private Long gmtCreate;
    private Long gmtModified;
    User user;
}
