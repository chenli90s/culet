package dao;

import java.util.List;

import entity.Comments;

public interface CommentsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated Tue Mar 07 22:06:13 CST 2017
     */
    int deleteByPrimaryKey(String cid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated Tue Mar 07 22:06:13 CST 2017
     */
    int insert(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated Tue Mar 07 22:06:13 CST 2017
     */
    int insertSelective(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated Tue Mar 07 22:06:13 CST 2017
     */
    Comments selectByPrimaryKey(String cid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated Tue Mar 07 22:06:13 CST 2017
     */
    int updateByPrimaryKeySelective(Comments record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table comments
     *
     * @mbggenerated Tue Mar 07 22:06:13 CST 2017
     */
    int updateByPrimaryKey(Comments record);

    List<Comments> foundCommentByAnnounceId(String currentAnnounce);
}