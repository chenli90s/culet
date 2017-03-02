package service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import dao.PersonMapper;
import entity.Person;

/**
 * @author Chenli
 * @time 2017/3/1 9:09
 */
@Service("testService")
public class TestServiceImpl {
    @Resource
    private PersonMapper personMapper;

    public void say(){
        System.out.println("hello world...");
    }


    public int insert(Person person){
        return personMapper.insert(person);
    }


}
