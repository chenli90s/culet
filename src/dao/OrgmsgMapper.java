package dao;

import entity.Orgmsg;

public interface OrgmsgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgmsg
     *
     * @mbggenerated Thu Mar 16 14:33:20 CST 2017
     */
    int insert(Orgmsg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orgmsg
     *
     * @mbggenerated Thu Mar 16 14:33:20 CST 2017
     */
    int insertSelective(Orgmsg record);

    Orgmsg selectOrgmsgById(String id);
}