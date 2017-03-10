package controller;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.Activity;
import entity.User;
import entity.Vote;
import global.Constants;
import service.ActivityService;
import utils.JsonMseeageFactory;
import utils.JsonUtils;
import utils.UUIDUtils;
import utils.UploadFilesUtils;

/**
 * @author Chenli
 * @time 2017/3/9 10:12
 * @updateDesc 工具卡了 code都回退了 -。。-；
 */
@Controller
@RequestMapping("activity")
public class ActivityController {

    @Resource
    public ActivityService activityService;
    /**
     * 发起活动
     * @param session
     * @param jsonActivity{activityId:
     *                     activityType: 投票。参与
     *                     activityTitle: --
     *                     activityDesc:--
     *                     activityDate:--
     *                     activityHot:
     *                     activityJoin:
     *                     activityLauncher:
     *                     activityAttribute:
     *                     activityComments:
     *                     activityCloseDate:--
     * @return
     */
    @RequestMapping("launchActivity.go")
    @ResponseBody
    public String launchActivity(HttpSession session,@RequestParam String jsonActivity){
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        Activity activity = (Activity) JsonUtils.string2Object(jsonActivity, Activity.class);
        if (user != null && activity != null){
            activity.setActivityid(UUIDUtils.getUUIDHex());
            activity.setActivitylauncher(user.getId());
            activity.setActivityattribute(user.getPartname());
            String result = activityService.setActivity(activity);
            return result;
        }
        return JsonMseeageFactory.makeErroMsg("提交参数不正确");
    }

    /***
     * 上传投票图片
     * @param request
     * @return
     */
    @RequestMapping("uploadVote.go")
    @ResponseBody
    public String upLoadVote(HttpServletRequest request){
        UploadFilesUtils uploadFilesUtils = new UploadFilesUtils();
        try {
            String files = uploadFilesUtils.upLoadFiles(request, "upload/vote/");
            if (files != null){
                return JsonMseeageFactory.makeSuccessMsg(files);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return JsonMseeageFactory.makeErroMsg("传输错误");
        }
        return JsonMseeageFactory.makeErroMsg("文件传输错误");
    }
    /**
     * 设置投票票详细
     * @param vote{voteId:
     *             voteActivityId: --
     *             voteImgHead: --
     *             voteDesc: --
     *             voteCount:}
     * @return
     */
    @RequestMapping("setVoteDesc.go")
    @ResponseBody
    public String setVoteDesc(@RequestParam String vote){
        Vote votes = (Vote) JsonUtils.string2Object(vote, Vote.class);
        if (votes != null){
            votes.setVoteid(UUIDUtils.getUUIDHex());
            String result = activityService.setVoteDesc(votes);
        }
        return null;
    }

    /**
     * 参加活动
     * @param session
     * @param join  {activityId:
     *               states:
     * @return
     */
    @RequestMapping("joinActivity.go")
    @ResponseBody
    public String joinActivity(HttpSession session,@RequestParam String join ){
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if (user != null) {
            try {
                JsonNode json = JsonUtils.string2Json(join);
                String activityId = json.get("activityId").toString();
                String states = json.get("states").toString();
                if (states == "1") {
                    //参与
                    String result = activityService.joinActivity(activityId,user.getNiclname());
                    return result;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return JsonMseeageFactory.makeErroMsg("参数上传不正确");
            }
        }
        return JsonMseeageFactory.makeErroMsg("未登录");
    }

}
