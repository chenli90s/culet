package service;

import java.util.List;

import entity.Comments;


public interface CommentsService {



    public int insert(Comments pojo);

    public int insertSelective(Comments pojo);

    public int insertList(List<Comments> pojos);

    public int update(Comments pojo);

    public int insertComment(Comments comments);

    String getCommentsByAnnounceId(String currentAnnounce);

    String addComments(Comments comments);
}
