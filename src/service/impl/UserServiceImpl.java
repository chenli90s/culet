package service.impl;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import dao.PatriarchMapper;
import dao.StudentMapper;
import dao.TeacherMapper;
import dao.UserMapper;
import entity.Patriarch;
import entity.Student;
import entity.Teacher;
import entity.User;
import exception.ParterException;
import global.Constants;
import service.UserService;
import utils.JsonUtils;
import utils.UUIDUtils;

/**
 * @author Chenli
 * @time 2017/3/2 10:27
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private PatriarchMapper patriarchMapper;

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

    @Override
    public void bindParter(JsonNode jsonNode,User user) throws ParterException{
        String parter = jsonNode.get("parter").toString();
        String parterId = UUIDUtils.getUUIDHex();
        switch (parter){
            case "teacher":
                //绑定老师
                Teacher teacher = (Teacher) JsonUtils.string2Object(jsonNode.get("data"), Teacher.class);
                user.setPartid(parterId);
                user.setPartname(Constants.PARTER_TEACHER);
                teacher.setId(parterId);
                updateUser(user);
                teacherMapper.insertSelective(teacher);
                break;
            case "student":
                //绑定学生
                Student student = (Student)JsonUtils.string2Object(jsonNode.get("data"),User.class);
                user.setPartid(parterId);
                user.setPartname(Constants.PARTER_TEACHER);
                student.setId(parterId);
                updateUser(user);
                studentMapper.insertSelective(student);
                break;
            case "patriarch":
                //绑定家长
                Patriarch patriarch = (Patriarch)JsonUtils.string2Object(jsonNode.get("data"),Patriarch.class);
                user.setPartid(parterId);
                user.setPartname(Constants.PARTER_TEACHER);
                patriarch.setId(parterId);
                updateUser(user);
                patriarchMapper.insertSelective(patriarch);
                break;
            default:
                throw new ParterException("没有此角色！！");
        }
    }
}
