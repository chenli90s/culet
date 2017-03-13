package service.impl;

import org.springframework.stereotype.Service;

import java.util.Date;

import javax.annotation.Resource;

import dao.ActivityMapper;
import dao.ActivitycommentsMapper;
import dao.VoteMapper;
import entity.Activity;
import entity.Activitycomments;
import entity.Vote;
import service.ActivityService;
import utils.JsonMseeageFactory;
import utils.UUIDUtils;

/**
 * @author Chenli
 * @time 2017/3/10 9:23
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;
    @Resource
    private VoteMapper voteMapper;
    @Resource
    private ActivitycommentsMapper activitycommentsMapper;

    @Override
    public String setActivity(Activity activity) {
        int i = activityMapper.insertSelective(activity);
        return JsonMseeageFactory.makeSuccessMsg("发起成功");
    }

    @Override
    public String setVoteDesc(Vote votes) {
        int i = voteMapper.insertSelective(votes);
        return JsonMseeageFactory.makeSuccessMsg("插入成功");
    }

    @Override
    public String joinActivity(String activityId, String niclname) {
        Activity activity = activityMapper.selectActivityJoinById(activityId);
        String activityjoin = activity.getActivityjoin();
        String newActivityjoin = activityjoin+";"+niclname;
        activity.setActivityjoin(newActivityjoin);
        int i = activityMapper.updateActivity(activity);
        return JsonMseeageFactory.makeSuccessMsg("success");
    }

    @Override
    public String addComments(Activitycomments activitycomments) {
        activitycomments.setCid(UUIDUtils.getUUIDHex());
        activitycomments.setCondate(new Date());
        activitycommentsMapper.insertSelective(activitycomments);
        return JsonMseeageFactory.makeSuccessMsg("已评论");
    }

    @Override
    public String updateHotByActivityId(String activityId) {
        Activity activity = activityMapper.selectActivityJoinById(activityId);
        if (activity != null) {
            Integer activityhot = activity.getActivityhot();
            activityhot += 1;
            activity.setActivityhot(activityhot);
            activityMapper.updateActivity(activity);
            return JsonMseeageFactory.makeSuccessMsg("已赞");
        }
        return JsonMseeageFactory.makeErroMsg("活动Id查找不到");
    }

    @Override
    public Activity selectActivity(String activityId) {
        Activity activity = activityMapper.selectActivityJoinById(activityId);
        return activity;
    }

    @Override
    public String delectActivityById(String activityId) {
        activityMapper.delectActivityById(activityId);
        return JsonMseeageFactory.makeSuccessMsg("已删除");
    }
}
