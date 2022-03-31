package shuodog.community.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuodog.community.mapper.UserMapper;
import shuodog.community.model.User;
import shuodog.community.model.UserExample;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user)
    {
//        User userData = userMapper.findByAccountId(user.getAccountId());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users=userMapper.selectByExample(userExample);

        if (users.size()==0)
        {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            userMapper.insert(user);
            System.out.println("创建成功");
        }
        else
        {

            User userData = new User();
            userData.setGmtModified(System.currentTimeMillis());
            userData.setName(user.getName());
            userData.setAvatarUrl(user.getAvatarUrl());
            userData.setToken(user.getToken());

//            userMapper.update(userData);

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(users.get(0).getId());
            userMapper.updateByExampleSelective(userData,example);
            System.out.println("修改成功");
        }
    }
}
