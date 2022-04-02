package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.PaginationDTO;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage",defaultValue = "1")Integer currentPage,
                        @RequestParam(name = "limit",defaultValue = "6")Integer limit,
                        Model model) {

        //根据用户查询来统合代码，统一参数，用户ID为user表主键，不可能为负数，故以此来判断是首页查询
        PaginationDTO paginationDTO = questionService.list(-1,currentPage, limit);

        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
