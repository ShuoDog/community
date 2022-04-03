package shuodog.community.dto;

import lombok.Data;

@Data
public class CommentTransmitDTO {
    private Integer parentId;
    private Integer type;
    private String content;
}
