package service;

import entity.Activity;
import entity.Vote;

/**
 * @author Chenli
 * @time 2017/3/10 8:58
 */
public interface ActivityService {

    String setActivity(Activity activity);

    String setVoteDesc(Vote votes);

    String joinActivity(String activityId, String niclname);
}
