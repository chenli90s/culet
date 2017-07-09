package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import dao.AnnounceMapper;
import dao.UserMapper;
import entity.Announce;
import entity.PageParam;
import entity.User;
import service.AnnounceService;
import utils.JsonMseeageFactory;
import utils.UUIDUtils;

@Service("announceService")
public class AnnounceServiceImpl implements AnnounceService{

    @Resource
    private AnnounceMapper announceMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public int insert(Announce pojo) {
        return 0;
    }

    @Override
    public int insertSelective(Announce pojo) {
        return 0;
    }

    @Override
    public int insertList(List<Announce> pojos) {
        return 0;
    }

    @Override
    public int update(Announce pojo) {
        return 0;
    }

    @Override
    public int insertAnnounce(Announce pojo) {
        return 0;
    }

    @Override
    public List<Announce> selectAnnounce() {
        return null;
    }

    @Override
    public Announce selectAnnounceAndcomments(String id) {
        Announce announce = announceMapper.selectByPrimaryKey(id);
        return announce;
    }

    @Override
    public void addAnnounce(User user, Announce announce) {
        String userId = user.getId();
        String announceId = UUIDUtils.getUUIDHex();
        announce.setAid(announceId);
        announce.setUserid(userId);
        announce.setDate(new Date());
        announce.setAttribute(user.getPartname());
        announceMapper.insertSelective(announce);
    }

    @Override
    public int updateHotById(String aId) {
        Announce announce = announceMapper.selectByPrimaryKey(aId);
        Integer hot = announce.getHot();
        hot += 1;
        announce.setHot(hot);
        return announceMapper.updateByPrimaryKeySelective(announce);
    }
    /**
     * 获取一些发言
     * @param jsonNode {一次请求多少个(requestNum：，当前分页数(currentPage}
     * @return {当前分页内容(currentContent)，当前分页数(currentPage)，分页总数(totalPage)}
     */
    @Override
    public String getUserAnnounce(JsonNode jsonNode,String id) {
        JsonNode requestNum = jsonNode.get("requestNum");
        JsonNode currentPage = jsonNode.get("currentPage");
        List<Announce> list = announceMapper.getUserAnnounce(new PageParam(Integer.parseInt(
                requestNum.toString()),
                Integer.parseInt(currentPage.toString()),id));
        if (list!=null &&list.size()>0){
            ObjectMapper objectMapper = new ObjectMapper();
            Iterator<Announce> iterator = list.iterator();
            while(iterator.hasNext()){
                Announce next = iterator.next();
                User user = userMapper.selectByPrimaryKey(next.getUserid());
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("imgPath","/upload/user/headimg/"+user.getHead());
                objectNode.put("name",user.getNiclname());
                String s = objectNode.toString();
                next.setUserid(s);
            }
            String jsonStr = null;
            try {
                jsonStr = objectMapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return JsonMseeageFactory.makeErroMsg("没有查找到相关发言，可能为空");
            }
            return jsonStr;
        }
        return JsonMseeageFactory.makeErroMsg("没有查找到相关发言，可能为空");
    }

    @Override
    public String getUserAnnounce(PageParam pageParam) {
        List<Announce> list = announceMapper.getUserAnnounce(pageParam);
        if (list!=null &&list.size()>0){
            ObjectMapper objectMapper = new ObjectMapper();
            Iterator<Announce> iterator = list.iterator();
            while(iterator.hasNext()){
                Announce next = iterator.next();
                User user = userMapper.selectByPrimaryKey(next.getUserid());
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("imgPath","/upload/user/headimg/"+user.getHead());
                objectNode.put("name",user.getNiclname());
                String s = objectNode.toString();
                next.setUserid(s);
            }
            String jsonStr = null;
            try {
                jsonStr = objectMapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return JsonMseeageFactory.makeErroMsg("没有查找到相关发言，可能为空");
            }
            return jsonStr;
        }
        return JsonMseeageFactory.makeErroMsg("没有查找到相关发言，可能为空");
    }

    @Override
    public String dispacherAnnounce(Announce announceobj) {
        String content = announceMapper.findSourceAnnounceById(announceobj.getAid());
        if (content != null){
            String contents = announceobj.getContent() + "::" + content;
            announceobj.setContent(contents);
            announceMapper.insertSelective(announceobj);
            return JsonMseeageFactory.makeSuccessMsg("转发成功");
        }
        return JsonMseeageFactory.makeErroMsg("失败，可能由于发言Id不正确");
    }

    @Override
    public String  delectAnnounce(String aid) {
        announceMapper.deleteByPrimaryKey(aid);
        return JsonMseeageFactory.makeSuccessMsg("success");
    }
}
