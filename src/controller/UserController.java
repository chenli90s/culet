package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entity.ResultJson;
import entity.User;
import exception.ParterException;
import global.Constants;
import service.UserService;
import utils.ImgStreamUtils;
import utils.JsonUtils;
import utils.SendEmail;
import utils.UUIDUtils;

/**
 * @author Chenli
 * @time 2017/3/2 10:32
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "checkAccount.go")
    @ResponseBody
    public String checkAccount(@RequestParam String usernames) throws IOException {
        JsonNode jsonNode = JsonUtils.string2Json(usernames).get("username");
        String s = userService.selectByUsername(usernames.toString());
        ObjectMapper mapper = new ObjectMapper();
        if (s == null){
            String result = mapper.writeValueAsString(
                    new ResultJson(true,"用户名不存在"));
            return result;
        }
        return mapper.writeValueAsString(new ResultJson(false,"用户名存在"));
    }


    @RequestMapping(value = "registUser.go", method = RequestMethod.POST)
    @ResponseBody
    public String registUser(@RequestBody String  users, HttpServletRequest request, HttpServletRequest response) throws JsonProcessingException {
        User user = (User) JsonUtils.string2Object(users,User.class);
        ObjectMapper mapper = new ObjectMapper();
        if (user!=null && userService.selectByUsername(user.getUsername()) == null){
            System.out.println(user);
            String id = UUIDUtils.getUUIDHex();
            user.setId(id);
            userService.createUser(user);
            SendEmail.send(user.geteMail(),id);
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(Constants.SESSION_MAX_INTERVAL);
            session.setAttribute("user",user);
            return mapper.writeValueAsString(new ResultJson(false,"注册成功"));
        }
        return mapper.writeValueAsString(new ResultJson(true,"注册失败"));
    }

    @RequestMapping(value = "loginUser.go", method = RequestMethod.POST)
    @ResponseBody
    public String loginUser(@RequestBody String users,HttpServletRequest request) throws JsonProcessingException {
        User user = (User) JsonUtils.string2Object(users, User.class);
        ObjectMapper mapper = new ObjectMapper();
        if ((user != null) && userService.selectByUsername(user.getUsername()) != null){
            User userByUsername = userService.findUserByUsername(user.getUsername());
            if (userByUsername.equals(user)){
                request.getSession().setMaxInactiveInterval(Constants.SESSION_MAX_INTERVAL);
                request.getSession().setAttribute("user",userByUsername);
                String head = user.getHead();
                String realPath = request.getServletContext().getRealPath("upload/user/headimg/");
                String result = ImgStreamUtils.setHeadImgPath(realPath+head);
                return mapper.writeValueAsString(
                        new ResultJson(false,"登陆成功",result));
            }
        }
        return mapper.writeValueAsString(
                new ResultJson(0,"登陆失败"));
    }

    @RequestMapping(value = "getUserInfo.go",method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo(HttpServletRequest request) throws JsonProcessingException {
        User user = (User) request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        if (user !=null ){
            User userByUsername = userService.findUserByUsername(user.getUsername());
            userByUsername.setId("");
            return mapper.writeValueAsString(userByUsername);
        }
        return mapper.writeValueAsString(new ResultJson(0,"未登录获取失败"));
    }

    @RequestMapping(value = "updataHeadImg.go")
    @ResponseBody
    public String updateHeadImg(HttpServletRequest request) throws JsonProcessingException {
        User attribute = (User) request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        if (attribute != null){
            CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                    request.getSession().getServletContext());
            if (resolver.isMultipart(request)){
                //将request变成多个部分的request
                MultipartHttpServletRequest multipartHttpServletRequest =
                        (MultipartHttpServletRequest) request;
                Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
                while (fileNames.hasNext()){
                    //遍历文件
                    MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next().toString());
                    if (file!=null){
                        String path = request.getSession().
                                getServletContext().getRealPath("upload/user/headimg/");
                        String filename = UUIDUtils.getUUIDHex()+file.getName().
                                substring(file.getName().indexOf("."));
                        try {
                            file.transferTo(new File(path+filename));
                            if (attribute.getHead()!=null) {
                                boolean delete = new File(path + attribute.getHead()).delete();
                            }
                            attribute.setHead(filename);
                            userService.updateUser(attribute);
                            return new ObjectMapper().writeValueAsString(new ResultJson(false,"上传成功",filename));
                        } catch (IOException e) {
                            e.printStackTrace();
                            return new ObjectMapper().writeValueAsString(new ResultJson(
                                    1,"上传失败"
                            ));
                        }
                    }
                }
            }
        }
        return new ObjectMapper().writeValueAsString(new ResultJson(
                1,"上传失败"
        ));
    }

    @RequestMapping(value = "setUserInfo.go",method = RequestMethod.POST)
    @ResponseBody
    public String setUserInfo(@RequestBody String users,HttpServletRequest request) throws JsonProcessingException {
        User user = (User) JsonUtils.string2Object(users, User.class);
        User attribute = (User) request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        if (attribute != null){
            user.setId(attribute.getId());
            userService.updateUser(user);
            return mapper.writeValueAsString(new ResultJson(1,"设置成功"));
        }
        return mapper.writeValueAsString(new ResultJson(0,"设置失败"));
    }

    @RequestMapping(value = "loginUserOut.go")
    @ResponseBody
    public String loginUserOut(HttpSession session){
        session.removeAttribute(Constants.USER_SESSION_NAME);
        return "success";
    }

    @RequestMapping(value = "bindParter.go",method = RequestMethod.POST)
    public String bindParter(HttpSession httpSession,@RequestParam String parter) throws IOException{
        if (httpSession.getAttribute(Constants.USER_SESSION_NAME)!=null
                && parter!=null){
            JsonNode jsonNode = JsonUtils.string2Json(parter);
            try {
                userService.bindParter(jsonNode,(User)httpSession.getAttribute(Constants.USER_SESSION_NAME));
            } catch (ParterException e) {
                e.printStackTrace();
                return JsonUtils.object2JsonStr(new ResultJson(0,"角色错误"));
            }
            return JsonUtils.object2JsonStr(new ResultJson(1,"绑定成功"));
        }
        return JsonUtils.object2JsonStr(new ResultJson(0,"角色错误"));
    }

}
