package shuodog.community.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer commentator;
    private Integer parentId;
    private String content;
    private Integer type;
    private Integer like_count;
    private Long gmtCreate;
    private Long gmtModified;
}
