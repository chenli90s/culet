package service;

import entity.User;

/**
 * @author Chenli
 * @time 2017/3/2 10:02
 */
public interface UserService {

    public int createUser(User user);

    public User findUserById(String id);

    public int updateUser(User user);

    public String selectByUsername(String username);

    public User findUserByUsername(String username);
}
