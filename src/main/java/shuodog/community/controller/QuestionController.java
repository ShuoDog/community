package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.CommentService;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.PaginationDTO;
import shuodog.community.dto.QuestionDTO;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           @RequestParam(name = "currentPage",defaultValue = "1")Integer currentPage,
                           @RequestParam(name = "limit",defaultValue = "6")Integer limit,
                           Model model) {
        questionService.addReadCount(id);
        QuestionDTO questionDTO =questionService.getById(id);
        PaginationDTO oneList=commentService.list(id,currentPage,limit);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("oneList",oneList);
        return "question";
    }
}
