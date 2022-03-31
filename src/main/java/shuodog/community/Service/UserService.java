package shuodog.community.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.User;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user)
    {
        User userData = userMapper.findByAccountId(user.getAccountId());
        if (userData==null)
        {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);
            System.out.println("创建用户成功");
        }
        else
        {
            userData.setGmtModified(System.currentTimeMillis());
            userData.setName(user.getName());
            userData.setAvatarUrl(user.getAvatarUrl());
            userData.setToken(user.getToken());
            userMapper.update(userData);
            System.out.println("重新登录成功");
        }
    }
}
