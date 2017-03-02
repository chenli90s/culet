package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Controller;
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
import global.Constants;
import service.UserService;
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
    public String checkAccount(@RequestParam String username) throws JsonProcessingException {
        String s = userService.selectByUsername(username);
        ObjectMapper mapper = new ObjectMapper();
        if (s == null){
            String result = mapper.writeValueAsString(
                    new ResultJson(true,"用户名不存在"));
            return result;
        }
        return mapper.writeValueAsString(new ResultJson(false,"用户名存在"));
    }


    @RequestMapping(value = "registUser.go", method = RequestMethod.GET)
    @ResponseBody
    public String registUser(User user, HttpServletRequest request, HttpServletRequest response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (user!=null && userService.selectByUsername(user.getUsername()) == null){
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

    @RequestMapping(value = "loginUser.go", method = RequestMethod.GET)
    @ResponseBody
    public String loginUser(User user,HttpServletRequest request) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if ((user != null) && userService.selectByUsername(user.getUsername()) == null){
            User userByUsername = userService.findUserByUsername(user.getUsername());
            if (userByUsername.equals(user)){
                request.getSession().setMaxInactiveInterval(Constants.SESSION_MAX_INTERVAL);
                request.getSession().setAttribute("user",userByUsername);
                return mapper.writeValueAsString(
                        new ResultJson(1,"登陆成功"));
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
            return mapper.writeValueAsString(userByUsername);
        }
        return mapper.writeValueAsString(new ResultJson(0,"未登录获取失败"));
    }

    @RequestMapping(value = "updataHeadImg")
    @ResponseBody
    public String updateHeadImg(HttpServletRequest request,User user) throws JsonProcessingException {
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
                                getServletContext().getRealPath("upl0oad/user/headimg/");
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
    public String setUserInfo(User user,HttpServletRequest request) throws JsonProcessingException {
        User attribute = (User) request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        if (attribute != null){
            user.setId(attribute.getId());
            userService.updateUser(user);
            return mapper.writeValueAsString(new ResultJson(1,"设置成功"));
        }
        return mapper.writeValueAsString(new ResultJson(0,"设置失败"));
    }
}
