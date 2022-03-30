package shuodog.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shuodog.community.Service.UserService;
import shuodog.community.dto.AccessTokenDTO;
import shuodog.community.dto.GithubUser;
import shuodog.community.model.User;
import shuodog.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserService userService;

    @Value("${github.client.id}")
    private String CLIENT_ID;
    @Value("${github.client.secret}")
    private String CLIENT_SECRET;
    @Value("${github.redirect.uri}")
    private String REDIRECT_URL;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(CLIENT_ID);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(CLIENT_SECRET);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(REDIRECT_URL);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            String token = UUID.randomUUID().toString();//随机生产的字符串作为唯一的标识符放在cookie里
            user.setToken(token);
            user.setGmtCreate(System.currentTimeMillis());//获取生成user对象的时间
            user.setGmtModified(user.getGmtCreate());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response
    ){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        System.out.println("退出登录成功");
        return "redirect:/";
    }
}
