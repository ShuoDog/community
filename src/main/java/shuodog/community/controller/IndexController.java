package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.PaginationDto;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage",defaultValue = "1")Integer currentPage,
                        @RequestParam(name = "size",defaultValue = "2")Integer size,
                        Model model) {

        PaginationDto paginationDto = questionService.list(currentPage,size);
        model.addAttribute("paginationDto", paginationDto);
        return "index";
    }
}
