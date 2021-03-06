package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.QuestionService;
import shuodog.community.dto.QuestionDto;
import shuodog.community.model.Question;
import shuodog.community.model.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model
    ) {
        QuestionDto questionDto = questionService.getById(id);
        model.addAttribute("title", questionDto.getTitle());
        model.addAttribute("description", questionDto.getDescription());
        model.addAttribute("tag", questionDto.getTag());
        model.addAttribute("id",questionDto.getId());
        return "publish";
    }


    @PostMapping("/publish")
    public String postPublish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "id",required = false)Integer id,
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) request.getSession().getAttribute("user");

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (user == null) {
            model.addAttribute("error", "???????????????");
            return "publish";
        } else if (title == null || title == "") {
            model.addAttribute("error", "??????????????????");
            return "publish";
        } else if (description == null || description == "") {
            model.addAttribute("error", "??????????????????");
            return "publish";
        } else if (tag == null || tag == "") {
            model.addAttribute("error", "??????????????????");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        question.setId(id);//???????????????????????????????????????

        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
