package service;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

import entity.Announce;
import entity.PageParam;
import entity.User;


public interface AnnounceService{


    public int insert(Announce pojo);

    public int insertSelective(Announce pojo);

    public int insertList(List<Announce> pojos);

    public int update(Announce pojo);

    public int insertAnnounce(Announce pojo);

    public List<Announce> selectAnnounce();

    public Announce selectAnnounceAndcomments(String id);

    public void addAnnounce(User user, Announce announce);

    int updateHotById(String aId);

    String getUserAnnounce(JsonNode jsonNode,String id);

    String getUserAnnounce(PageParam pageParam);

    String dispacherAnnounce(Announce announceobj);

    String delectAnnounce(String aid);
}
