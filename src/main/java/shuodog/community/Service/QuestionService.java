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
        PaginationDto paginationDto = new PaginationDto();
        Integer total = questionMapper.count();
        paginationDto.setPagination(total,currentPage,size);

        if(currentPage<1)currentPage=1;
        else if(currentPage>paginationDto.getTotalPage())currentPage=paginationDto.getTotalPage();

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
}
