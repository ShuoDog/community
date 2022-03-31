package shuodog.community.Service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuodog.community.dto.PaginationDto;
import shuodog.community.dto.QuestionDto;
import shuodog.community.mapper.QuestionMapper;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.Question;
import shuodog.community.model.QuestionExample;
import shuodog.community.model.User;
import shuodog.community.model.UserExample;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDto list(Integer currentPage, Integer limit) {
        Integer totalPage;

//        Integer total = questionMapper.count();

        Integer total = (int) questionMapper.countByExample(new QuestionExample());

        PaginationDto paginationDto = new PaginationDto();

        if (total <= 1) totalPage = 1;
        else if (total % limit == 0) {
            totalPage = total / limit;
        } else {
            totalPage = total / limit + 1;
        }

        if (currentPage <= 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        paginationDto.setPagination(totalPage, currentPage);

        Integer offset = limit * (currentPage - 1);

//        List<Question> questionList=questionMapper.list(offset,limit)

        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));

        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questionList) {

//            User user =userMapper.findByID(question.getCreator());

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);

            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(users.get(0));
            questionDtoList.add(questionDto);
        }

        paginationDto.setQuestionDtoList(questionDtoList);

        return paginationDto;
    }





    public PaginationDto list(Integer userId, Integer currentPage, Integer limit) {

        Integer totalPage;
        
//        Integer total = questionMapper.countByUserId(userId);

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer total = (int) questionMapper.countByExample(questionExample);

        PaginationDto paginationDto = new PaginationDto();

        if (total <= 1) totalPage = 1;
        else if (total % limit == 0) {
            totalPage = total / limit;
        } else {
            totalPage = total / limit + 1;
        }

        if (currentPage <= 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        paginationDto.setPagination(totalPage, currentPage);

        Integer offset = limit * (currentPage - 1);

//        List<Question> questionList = questionMapper.listByUserId(userId, offset, limit);

        QuestionExample example =new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, limit));

        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questionList) {

//            User user =userMapper.findByID(question.getCreator());

            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);

            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(users.get(0));
            questionDtoList.add(questionDto);
        }

        paginationDto.setQuestionDtoList(questionDtoList);

        return paginationDto;
    }


    public QuestionDto getById(Integer id) {

//        Question question = questionMapper.getById(id);

        Question question=questionMapper.selectByPrimaryKey(id);

        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);

//        User user =userMapper.findByID(question.getCreator());

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        questionDto.setUser(users.get(0));

        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
            System.out.println("创建问题成功");
        } else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample =new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(question,questionExample);
            System.out.println("修改问题成功");
        }
    }
}
