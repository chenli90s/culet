package controller;

import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import entity.Announce;
import entity.Comments;
import entity.PageParam;
import entity.User;
import global.Constants;
import service.AnnounceService;
import service.CommentsService;
import utils.JsonMseeageFactory;
import utils.JsonUtils;
import utils.UploadFilesUtils;

/**
 * @author Chenli
 * @time 2017/3/7 16:55
 */
@Controller
@RequestMapping("/announce")
public class AnnounceController {

    @Resource
    private AnnounceService announceService;
    @Resource
    private CommentsService commentsService;

    /**
     * 发言
     * @param session
     * @param str   {"aid":"",  ---->发言id
     *                  "status":"yum", ----->转发者
     *                  "content":"天", -----> 正文
     *                  "date":,     ----> 日期
     *                  "hot":59,    -----> 热度
     *                  "attribute":"teacher",  ----->转发域
     *                  "userid":"",   -----> 用户id
     *                  "comments":null}  ---->  不管
     * @return
     */
    @RequestMapping("addAnnounce.go")
    @ResponseBody
    public String addAnnounce(HttpSession session,@RequestBody String str){
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        /*byte[] bytes = str.getBytes();
        for(int i = 0;i<=bytes.length;i++){
            System.out.println(bytes[i]);
        }*/
        Announce announce = (Announce) JsonUtils.string2Object(str, Announce.class);
        if (user != null && announce != null){
            announceService.addAnnounce(user,announce);
            return JsonMseeageFactory.makeSuccessMsg("发言成功");
        }
        return JsonMseeageFactory.makeErroMsg("发言失败,可能由于未登录或上传字段错误");
    }
    /**
     * 获取一些发言
     * @param string {一次请求多少个(requestNum：，当前分页数(currentPage}
     * @return {当前分页内容(currentContent)，当前分页数(currentPage)，分页总数(totalPage)}
     */
    @RequestMapping("getAnnounce.go")
    @ResponseBody
    public String getAnnounce(HttpSession session ,@RequestBody String string) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if (string != null && user != null) {
            JsonNode jsonNode = JsonUtils.string2Json(string);
            String result = announceService.getUserAnnounce(jsonNode,user.getId());
            return result;
        }
        return new JsonMseeageFactory().makeErroMsg("参数不能为空");
    }

    /**
     * 添加评论
     * @param session
     * @param commets  {"cid":"", ---->null
     *                 "statues":"15", ----->评论人
     *                 "target":"哈哈",------>@的人(默认为楼主)
     *                 "comment":"这个",------>内容
     *                 "condate":1488813050000, --------->日期
     *                 "acid":"", ------------>发言id（必填）
     *                 "announce":null}
     * @return
     */
    @RequestMapping("addComents.go")
    @ResponseBody
    public String addComents(HttpSession session,@RequestParam String commets){
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        Comments comments = (Comments) JsonUtils.string2Object(commets, Comments.class);
        if (comments != null){
            comments.setStatues(user.getUsername());
            String result = commentsService.addComments(comments);
            return result;
        }
        return JsonMseeageFactory.makeErroMsg("上传参数无法解析");
    }
    /**
     * 获取当前发言的评论
     * @param currentAnnounce {currentAnnounceId}
     * @return
     */
    @RequestMapping("getAnnounceComments.go")
    @ResponseBody
    public String getAnnounceComments(@RequestBody String currentAnnounce){
        JsonNode jsonNode = null;
        try {
            jsonNode = JsonUtils.string2Json(currentAnnounce);
            String currentAnnounceId = jsonNode.get("currentAnnounceId").toString();
            String result = commentsService.getCommentsByAnnounceId(currentAnnounce);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JsonMseeageFactory.makeErroMsg("上传参数错误");
    }

    /**
     * 点赞
     * @param session
     * @param aId
     * @return
     */
    @RequestMapping("likeAnnounce.go")
    @ResponseBody
    public String likeAnnounce(HttpSession session, @RequestParam String aId){
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if (user!=null && aId!=null)announceService.updateHotById(aId);
        return JsonMseeageFactory.makeSuccessMsg("已送达");
    }

    /**
     * 转发
     * @param httpSession
     * @param announce  {"aid":"",  ---->发言id（转发的id必填）
     *                  "status":"yum", ----->转发者（不管）
     *                  "content":"天", -----> 正文 （转发评论）
     *                  "date":,     ----> 日期
     *                  "hot":59,    -----> 热度 (填写)
     *                  "attribute":"teacher",  ----->转发域 (填写)
     *                  "userid":"",   -----> 用户id
     *                  "comments":null}  ---->  不管
     * @return
     */
    @RequestMapping("dispacherAnnounce.go")
    @ResponseBody
    public String dispacherAnnounce(HttpSession httpSession,@RequestParam String announce){
        User user = (User) httpSession.getAttribute(Constants.USER_SESSION_NAME);
        Announce announceobj = (Announce) JsonUtils.string2Object(announce, Announce.class);
        if (user !=null && announceobj != null){
            announceobj.setStatus(user.getUsername());
            announceobj.setDate(new Date());
            announceobj.setUserid(user.getId());
            String result = announceService.dispacherAnnounce(announceobj);
            return result;
        }
        return JsonMseeageFactory.makeErroMsg("上传参数不正确");
    }

    @RequestMapping("delectAnnounce.go")
    @ResponseBody
    public String delectAnnounce(HttpSession session,@RequestParam String aid){
        //System.out.println(aid);
        Announce announce = announceService.selectAnnounceAndcomments(aid);
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if (announce.getUserid().equals(user.getId())) {
            String delectAnnounce = announceService.delectAnnounce(aid);
            return delectAnnounce;
        }

        return JsonMseeageFactory.makeErroMsg("无权删除");
    }

    @RequestMapping(value = "uploadImg.go", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(@RequestParam(value = "uploadFile") MultipartFile[] uploadFile,HttpSession session) throws IOException {
        /*String filePath = "/" + new SimpleDateFormat("yyyy").format(new Date()) + "/"
                + new SimpleDateFormat("MM").format(new Date()) + "/"
                + new SimpleDateFormat("dd").format(new Date()) +"/";*/
        String realPath = session.getServletContext().getRealPath("/upload/announce/");
        //filePath = realPath + filePath;
        String result = UploadFilesUtils.upLoadFiles(uploadFile, realPath);
        return result;
    }

    @RequestMapping(value = "uploaBaseImg.go",method = RequestMethod.POST)
    @ResponseBody
    public String uploadBaseImg(@RequestBody String data,HttpSession session){
        System.out.println(data);
        return null;
    }

    @RequestMapping(value = "loadMoreAnnounce.go",method = RequestMethod.POST)
    @ResponseBody
    public String loadMoreAnnounce(@RequestBody String page, HttpSession session) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if (page != null && user != null) {
            JsonNode jsonNode = JsonUtils.string2Json(page);
            String currentPage = jsonNode.get("currentPage").toString();
            String size = jsonNode.get("size").toString();
            String result = announceService.getUserAnnounce(
                    new PageParam(currentPage,size ,user.getId()));
            return result;
        }
        return new JsonMseeageFactory().makeErroMsg("参数不能为空");
    }
}
