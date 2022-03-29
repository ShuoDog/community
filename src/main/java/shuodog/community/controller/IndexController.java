package shuodog.community.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.PaginationDto;
import shuodog.community.dto.QuestionDto;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage",defaultValue = "1")Integer currentPage,
                        @RequestParam(name = "size",defaultValue = "2")Integer size,
                        HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDto paginationDto = questionService.list(currentPage,size);
        model.addAttribute("paginationDto", paginationDto);
        return "index";
    }
}
