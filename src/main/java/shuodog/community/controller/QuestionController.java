package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.QuestionDto;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model
    ) {
        QuestionDto questionDto =questionService.getById(id);
        model.addAttribute("questionDto",questionDto);
        return "question";
    }
}
