package service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import dao.UserMapper;
import entity.User;
import service.UserService;

/**
 * @author Chenli
 * @time 2017/3/2 10:27
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int createUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public User findUserById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public String selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
