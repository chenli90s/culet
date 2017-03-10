package service.impl;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import dao.ActivityMapper;
import dao.VoteMapper;
import entity.Activity;
import entity.Vote;
import service.ActivityService;
import utils.JsonMseeageFactory;

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
}
