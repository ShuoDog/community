package shuodog.community.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuodog.community.dto.PaginationDto;
import shuodog.community.dto.QuestionDto;
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


    public PaginationDto list(Integer userId,Integer currentPage,Integer limit)
    {
        Integer total;
        Integer totalPage;
        PaginationDto paginationDto = new PaginationDto();        
        if(userId==-1)
        {
            total = questionMapper.count();
        }
        else
        {
            total = questionMapper.countByUserId(userId);
        }

        if(total<=1)totalPage=1;
        else if(total%limit==0){
            totalPage=total/limit;
        }
        else {
            totalPage=total/limit+1;
        }

        if(currentPage<1)
        {
            currentPage=1;
        }
        else if(currentPage>totalPage)
        {
            currentPage=totalPage;
        }

        paginationDto.setPagination(totalPage,currentPage);

        Integer offset=limit*(currentPage-1);

        List<Question> questionList;

        if(userId==-1)
        {
            questionList=questionMapper.list(offset,limit);
        }
        else
        {
            questionList=questionMapper. listByUserId(userId,offset,limit);
        }


        List<QuestionDto> questionDtoList = new ArrayList<>();


        for (Question question:questionList){
            User user =userMapper.findByID(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

        paginationDto.setQuestionDtoList(questionDtoList);

        return paginationDto;
    }


    public QuestionDto getById(Integer id) {
        Question question=questionMapper.getById(id);
        if(question==null)
        {
            throw new ExceptionMessage(EnumExceptionImplements.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto=new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user =userMapper.findByID(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null)
        {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
            System.out.println("创建问题成功");
        }
        else
        {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
            System.out.println("修改问题成功");
        }
    }
}
