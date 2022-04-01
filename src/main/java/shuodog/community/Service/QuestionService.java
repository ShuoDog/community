package shuodog.community.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuodog.community.dto.PaginationDTO;
import shuodog.community.dto.QuestionDTO;
import shuodog.community.exception.ExceptionMessage;
import shuodog.community.exception.EnumExceptionImplements;
import shuodog.community.mapper.QuestionMapper;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.Question;
import shuodog.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    public PaginationDTO list(Integer userId, Integer currentPage, Integer limit) {
        Integer total;
        Integer totalPage;
        PaginationDTO paginationDTO = new PaginationDTO();
        if (userId == -1) {
            total = questionMapper.count();
        } else {
            total = questionMapper.countByUserId(userId);
        }

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

        List<Question> questionList;

        if (userId == -1) {
            questionList = questionMapper.list(offset, limit);
        } else {
            questionList = questionMapper.listByUserId(userId, offset, limit);
        }


        List<QuestionDTO> questionDTOList = new ArrayList<>();


        for (Question question : questionList) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOList);

        return paginationDTO;
    }


    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        if (question == null) {
            throw new ExceptionMessage(EnumExceptionImplements.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findByID(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
            System.out.println("创建问题成功");
        } else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
            System.out.println("修改问题成功");
        }
    }

    public void addReadCount(Integer id) {
        questionMapper.updateReadCount(id);
    }
}
