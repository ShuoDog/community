package shuodog.community.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shuodog.community.enums.CommentTypeEnum;
import shuodog.community.exception.EnumExceptionImplements;
import shuodog.community.exception.ExceptionMessage;
import shuodog.community.mapper.CommentMapper;
import shuodog.community.mapper.QuestionMapper;
import shuodog.community.model.Comment;
import shuodog.community.model.Question;

@Service
public class CommentService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    CommentMapper commentMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0)
        {
            throw new ExceptionMessage(EnumExceptionImplements.COMMENT_NOT_FOUND);
        }

        if(comment.getType()==null||!CommentTypeEnum.isExist(comment.getType()))
        {
            throw new ExceptionMessage(EnumExceptionImplements.TYPE_NOT_FOUND);
        }
        else if(comment.getType()==CommentTypeEnum.QUESTION.getType())
        {
            Question question = questionMapper.getById(comment.getParentId());
            if(question==null)
            {
                throw new ExceptionMessage(EnumExceptionImplements.QUESTION_NOT_FOUND);
            }
            questionMapper.updateCommentCount(question.getId());
        }
        else
        {
            Comment commentData = commentMapper.getById(comment.getId());
            if(commentData==null){
                throw new ExceptionMessage(EnumExceptionImplements.COMMENT_NOT_FOUND);
            }
        }
        commentMapper.insert(comment);
        System.out.println("评论成功");
    }
}
