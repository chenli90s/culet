package test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import dao.AnnounceMapper;
import dao.CommentsMapper;
import entity.Announce;
import entity.Comments;
import entity.PageParam;
import entity.Person;
import entity.User;
import service.AnnounceService;
import service.CommentsService;
import service.UserService;
import service.impl.TestServiceImpl;
import utils.JsonUtils;
import utils.UUIDUtils;

/**
 *
 *
 * @author Chenli
 * @time 2017/3/1 9:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring.xml"})
public class TestMargen {

    @Resource
    private TestServiceImpl testService;

    @Resource
    private UserService userService;

    @Resource
    private AnnounceService announceService;

    @Resource
    private CommentsService commentsService;

    @Resource
    private CommentsMapper commentsMapper;
    @Resource
    private AnnounceMapper announceMapper;

    @Test
    public void testSrping(){
        testService.say();
    }

    @Test
    public void testSpringAndMybitis(){
        testService.insert(new Person("12","nihao",15));
    }

    @Test
    public void testSpringMVC(){
        Person person = new Person("12", "hdjks", 16);

        System.out.println(person.toString());
    }

    @Test
    public void testMybatis(){
        User user = new User();
        user.setId("83859572953741fe95d8df7aca8c0d63");
        user.setGender(true);
        user.setMobile("18192869928");
        userService.updateUser(user);
    }

    @Test
    public void testAnnounce(){
        Announce announce = new Announce();
        announce.setAid(UUIDUtils.getUUIDHex());
        announce.setStatus("yum");
        announce.setContent("天地不仁，以万物为刍狗！\n" +
                "　　自太古以来，人类眼见周遭世界诸般奇异之事：电闪雷鸣、狂风暴雨，又有天灾人祸，哀鸿遍野，决非人力所能为，所能抵挡。遂以为九天之上，有诸般神灵；九幽之下，是阴魂归处，阎罗殿堂。于是神仙之说，流传于世。然而，纵然是世人眼中修真的仙人，终也脱不尽凡人的情欲，由此生出一段荡气回肠、曲折幽微的故事来。\n" +
                "　　柔弱少年入了天音寺与青云门的一段隐秘争斗之中，经历过险恶江湖，游历过神奇天地，在纷乱的红尘中，倔强地走着自己的路。苍天无尽，仙凡无边。一段与命运抗争的勇者传奇之旅就此展开……\n" +
                "\n");
        announce.setAttribute("teacher");
        announce.setDate(new Date());
        announce.setHot(59);

        announceService.insertAnnounce(announce);
    }

    @Test
    public void testComments() throws JsonProcessingException {
        List<Comments> commentss = commentsMapper.foundCommentByAnnounceId("057de5f534964b43b4ebc34dea1b149b");
        Comments comments = commentss.get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(comments));
    }
    @Test
    public void testCommentsSelect(){
        //Comments comments = commentsMapper.selectComments("057de5f534964b43b4ebc34dea1b149b");
        //System.out.println(comments.getAnnounce());

        String str = "{\"cid\":\"a25ff6f4d92640818b53c18fee2e2e6d\",\"" +
                "statues\":\"15\",\"target\":\"哈哈\",\"commen" +
                "t\":\"这个文章确实好\",\"condate\":1" +
                "488813050000,\"acid\":\"057de5f534964b43b4ebc3" +
                "4dea1b149b\"}";
        Comments comments = (Comments) JsonUtils.string2Object(str, Comments.class);
        System.out.println(comments.toString());

    }

    @Test
    public void testSelectAnnounce() throws JsonProcessingException {
        PageParam pageParam = new PageParam(2, 1, "057de5f534964b43b4ebc34dea1b149b");
        List<Announce> announces = announceMapper.getUserAnnounce(pageParam);
        //List<Comments> comments = announces.get(0).getComments();
        //Comments comments1 = comments.get(0);
        //System.out.println(JsonUtils.object2Json(announces.get(0)));
        Announce announce = announces.get(0);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(announce);
        System.out.println(s);
    }
}
