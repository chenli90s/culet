package dao;

import org.springframework.stereotype.Repository;

import entity.Person;

/**
 * @author Chenli
 * @time 2017/3/1 10:40
 */
@Repository
public interface PersonMapper {

    int insert(Person person);
}
