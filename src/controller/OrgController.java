package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import entity.Numorg;
import entity.Organization;
import entity.Orgmsg;
import entity.User;
import global.Constants;
import service.OrgService;
import utils.JdbcUtils;
import utils.JsonMseeageFactory;
import utils.JsonUtils;
import utils.UUIDUtils;

/**
 * @author Chenli
 * @time 2017/3/13 11:00
 */
@Controller
@RequestMapping("/org")
public class OrgController {

    @Resource
    private OrgService orgService;

    /**
     * //建立组织
     * @param session
     * @param org
     * {orgId:
     *  orgName:  ---->组织名字
     *  orgDesc:  ---->组织描述
     *  orgHead:  ---->组织头像（可为空）
     *  orgAdmin:
     * }
     * @return
     */
    @RequestMapping("buildOrg.go")
    @ResponseBody
    public String buildOrg(HttpSession session, @RequestParam String org){
        Organization organization = (Organization) JsonUtils.string2Object(org, Organization.class);
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if (organization!= null && user!=null){
            organization.setOrgid(UUIDUtils.getUUIDHex());
            organization.setOrgdate(new Date());
            organization.setOrgadmin(user.getId());
            String result = orgService.buildOrg(organization);
            return result;
        }
        return JsonMseeageFactory.makeErroMsg("上传参数有误");
    }

    //加入组织
    /**
     *
     * @param session
     * @param numOrg
     *  numorg{
     *  userId:
     *  orgId: ----->组织的Id
     *  numposition:
     *  numname: ------->成员名字（可为空）
     * }
     * @return
     */
    @RequestMapping("joinOrg.go")
    @ResponseBody
    public String joinOrg(HttpSession session, @RequestParam String numOrg){
        Numorg numorg = (Numorg) JsonUtils.string2Object(numOrg, Numorg.class);
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        if(numorg != null && user != null){
            numorg.setUserid(user.getId());
            String result = orgService.joinOrg(numorg);
            return result;
        }
        return JsonMseeageFactory.makeErroMsg("上传参数有误");
    }

    //发起报名

    /**
     *
     * @param session
     * @param orgMsg
     * orgmsg{
     *  orgid:  ----组织id
     *  msgType: ------信息类型
     *  msgData: ------信息数据
     *  msgFile: ------信息文件(里面为json对象的字符串)
     *  msgDate  ------信息日期
     * }
     * @return
     */
    @RequestMapping("createMsg.go")
    @ResponseBody
    public String createMsg(HttpSession session,@RequestParam String orgMsg) throws IOException, SQLException {
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);
        Orgmsg orgmsg = (Orgmsg) JsonUtils.string2Object(orgMsg, Orgmsg.class);
        if (orgmsg != null && user != null){
            orgmsg.setMsgdate(new Date());
            String msgtype = orgmsg.getMsgtype();
            if (msgtype.equals(Constants.ORG_MSG_ENTERING)){
                //创建表格
                String msgfile = orgmsg.getMsgfile();
                String[] split = msgfile.split(",");
                split[0] = split[0].substring(1);
                split[split.length-1] = split[split.length - 1].substring(0, split[split.length - 1].length() - 1);
                //String filename = ExcelUtils.makeSampleFile(split, session.getServletContext().getRealPath("upload/user/OrgExcel/"));
                String table = JdbcUtils.createTable(split);
                orgmsg.setMsgfile(table);
            }
            String result = orgService.createMsg(orgmsg);
            return result;
        }
        return JsonMseeageFactory.makeSuccessMsg("上传参数错误");
    }

    //查看报名
    @RequestMapping("descMsg")
    @ResponseBody
    public String descMsg(@RequestParam String msgFile) throws IOException, SQLException {
        Orgmsg orgmsg = orgService.selectOrgmsgById(msgFile);
        List<String> strings = JdbcUtils.showTables(orgmsg.getMsgfile());
        String s = JsonUtils.list2String(strings);
        orgmsg.setMsgfile(s);
        return JsonUtils.object2Json(orgmsg);
    }

    //参与报名
    /**
     *
     * @param session
     * @param msg {
     *            orgid:组织Id
     *            msgType:
     * }
     * @return
     */
    @RequestMapping("addMsg.go")
    @ResponseBody
    public String addMsg(HttpSession session,@RequestParam String msg) throws IOException {
        User user = (User) session.getAttribute(Constants.USER_SESSION_NAME);


        return null;
    }

    //退出组织
    @RequestMapping("exitOrg.go")
    @ResponseBody
    public String exitOrg(HttpSession session,@RequestParam String org){

        return null;
    }




}
