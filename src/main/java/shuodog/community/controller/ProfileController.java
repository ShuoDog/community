package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.PaginationDto;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(name = "currentPage",defaultValue = "1")Integer currentPage,
                          @RequestParam(name = "size",defaultValue = "1 ")Integer size,
                          HttpServletRequest request,
                          Model model) {

        User user=(User) request.getSession().getAttribute("user");

        if(user==null){
            System.out.println("用户未登录，回到首页");
            return "redirect:/";
        }

        if("questions".equals(action))
        {
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if("replies".equals(action))
        {
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PaginationDto paginationDto = questionService.list(user.getId(),currentPage,size);
        model.addAttribute("paginationDto",paginationDto);

        return "profile";

    }
}
