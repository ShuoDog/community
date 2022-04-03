package shuodog.community.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shuodog.community.dto.CommentDisplayDTO;
import shuodog.community.dto.PaginationDTO;
import shuodog.community.enums.CommentTypeEnum;
import shuodog.community.exception.EnumExceptionImplements;
import shuodog.community.exception.ExceptionMessage;
import shuodog.community.mapper.CommentMapper;
import shuodog.community.mapper.QuestionMapper;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.Comment;
import shuodog.community.model.Question;
import shuodog.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

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

    public PaginationDTO list(Integer id,Integer currentPage, Integer limit) {
        Integer total;
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        total = commentMapper.countByParentId(id);
        if (total <= 1) totalPage = 1;
        else if (total % limit == 0) {
            totalPage = total / limit;
        } else {
            totalPage = total / limit + 1;
        }
        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        paginationDTO.setPagination(totalPage, currentPage);

        Integer offset = limit * (currentPage - 1);

        List<Comment> commentList ;
        commentList= commentMapper.listByIdAndType(id,CommentTypeEnum.QUESTION.getType(),offset,limit);

        List<CommentDisplayDTO> commentDisplayDTOList = new ArrayList<>();

        for (Comment comment:commentList){
            User user = userMapper.findByID(comment.getCommentator());
            CommentDisplayDTO commentDisplayDTO= new CommentDisplayDTO();
            BeanUtils.copyProperties(comment,commentDisplayDTO);
            commentDisplayDTO.setUser(user);
            commentDisplayDTOList.add(commentDisplayDTO);
        }

        paginationDTO.setCommentDisplayDTOList(commentDisplayDTOList);

        return paginationDTO;
    }
}
