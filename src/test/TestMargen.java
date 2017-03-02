package test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import entity.Person;
import entity.User;
import service.UserService;
import service.impl.TestServiceImpl;

/**
 *
 *
 * @author Chenli
 * @time 2017/3/1 9:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring.xml"})
public class TestMargen {

    @Resource
    private TestServiceImpl testService;

    @Resource
    private UserService userService;


    @Test
    public void testSrping(){
        testService.say();
    }

    @Test
    public void testSpringAndMybitis(){
        testService.insert(new Person("12","nihao",15));
    }

    @Test
    public void testSpringMVC(){
        Person person = new Person("12", "hdjks", 16);

        System.out.println(person.toString());
    }

    @Test
    public void testMybatis(){
        User user = new User();
        user.setId("83859572953741fe95d8df7aca8c0d63");
        user.setGender(true);
        user.setMobile("18192869928");
        userService.updateUser(user);

    }
}
