package shuodog.community.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<QuestionDto> list()
    {
        List<Question> questionList=questionMapper.list();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question:questionList){
            User user =userMapper.findByID(question.getCreator());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
