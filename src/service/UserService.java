package service;

import com.fasterxml.jackson.databind.JsonNode;

import entity.User;
import exception.ParterException;

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

    public void bindParter(JsonNode jsonNode,User user) throws ParterException;
}
