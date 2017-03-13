package service;

import entity.Activity;
import entity.Activitycomments;
import entity.Vote;

/**
 * @author Chenli
 * @time 2017/3/10 8:58
 */
public interface ActivityService {

    String setActivity(Activity activity);

    String setVoteDesc(Vote votes);

    String joinActivity(String activityId, String niclname);

    String addComments(Activitycomments activitycomments);

    String updateHotByActivityId(String activityId);

    Activity selectActivity(String activityId);

    String delectActivityById(String activityId);
}
