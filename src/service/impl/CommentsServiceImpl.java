package service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import dao.CommentsMapper;
import entity.Comments;
import service.CommentsService;
import utils.JsonMseeageFactory;
import utils.UUIDUtils;

@Service("commentsService")
public class CommentsServiceImpl implements CommentsService{

    @Resource
    private CommentsMapper commentsMapper;

    @Override
    public int insert(Comments pojo) {
        return 0;
    }

    @Override
    public int insertSelective(Comments pojo) {
        return 0;
    }

    @Override
    public int insertList(List<Comments> pojos) {
        return 0;
    }

    @Override
    public int update(Comments pojo) {
        return 0;
    }

    @Override
    public int insertComment(Comments comments) {
        return 0;
    }

    @Override
    public String getCommentsByAnnounceId(String currentAnnounce) {
        List<Comments> commentss = commentsMapper.foundCommentByAnnounceId(currentAnnounce);
        if (commentss != null &&commentss.size()>0){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String json = objectMapper.writeValueAsString(commentss);
                return json;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return JsonMseeageFactory.makeErroMsg("comment类型转化错误");
    }

    /**
     * commets  {"cid":"", ---->null
     *                 "statues":"15", ----->备注状态
     *                 "target":"哈哈",------>@的人
     *                 "comment":"这个",------>内容
     *                 "condate":1488813050000, --------->日期
     *                 "acid":"", ------------>发言id（必填）
     *                 "announce":null} ----->不管
     * @param comments
     * @return
     */
    @Override
    public String addComments(Comments comments) {
        comments.setCid(UUIDUtils.getUUIDHex());
        comments.setCondate(new Date());
        commentsMapper.insertSelective(comments);
        return JsonMseeageFactory.makeSuccessMsg("已评论");
    }
}
