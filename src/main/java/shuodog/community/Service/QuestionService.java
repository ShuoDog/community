package shuodog.community.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuodog.community.dto.PaginationDto;
import shuodog.community.dto.QuestionDto;
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

    public PaginationDto list(Integer currentPage, Integer size)
    {
        Integer totalPage;
        Integer total = questionMapper.count();
        PaginationDto paginationDto = new PaginationDto();

        if(total==0)totalPage=1;
        else if(total%size==0){
            totalPage=total/size;
        }
        else {
            totalPage=total/size+1;
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

        Integer limit=size*(currentPage-1);
        List<Question> questionList=questionMapper.list(limit,size);
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

    public PaginationDto list(Integer userId,Integer currentPage,Integer size)
    {

        Integer totalPage;
        Integer total = questionMapper.countByUserId(userId);
        PaginationDto paginationDto = new PaginationDto();

        if(total==0)totalPage=1;
        else if(total%size==0){
            totalPage=total/size;
        }
        else {
            totalPage=total/size+1;
        }

        if(currentPage<1)
        {
            currentPage=1;
        }
        else if(currentPage>totalPage&&totalPage>0)
        {
            currentPage=totalPage;
        }

        paginationDto.setPagination(totalPage,currentPage);

        Integer limit=size*(currentPage-1);
        List<Question> questionList=questionMapper. listByUserId(userId,limit,size);
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
