package test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import entity.ResultJson;
import entity.User;
import utils.ExcelUtils;
import utils.JsonUtils;
import utils.UUIDUtils;

/**
 * @author Chenli
 * @time 2017/3/2 11:05
 */
public class TestUtils {

    @Test
    public void testUUid(){
        String uuidHex = UUIDUtils.getUUIDHex();
        System.out.println(uuidHex);
    }

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper json = new ObjectMapper();
        ResultJson resultJson = new ResultJson(true, "测试");
        byte[] buf = json.writeValueAsBytes(resultJson);
        String s = json.writeValueAsString(resultJson);
        for (int i = 0 ; i<buf.length;i++ ){
           System.out.print(buf[i]);
        }

        System.out.println("\n"+s);
    }

    @Test
    public void testString2Json() throws IOException {
        String str = JsonUtils.object2JsonStr(new ResultJson(true,
                "测试","chenli",new User("chenli",
                "64497","sdad",false)));
        System.out.println(str);
        JsonNode jsonNode = JsonUtils.string2Json(str);
        User user = (User) JsonUtils.string2Object(jsonNode.get("data"), User.class);
        System.out.println(jsonNode.get("data"));
        System.out.println(user.getUsername());
    }

    @Test
    public void testBase64Encode(){
        File  file = new File("C:\\Users\\Chenli\\Pictures\\陈立.jpg");
        //String img = ImgStreamUtils.baseImg("C:\\Users\\Chenli\\Pictures\\陈立.jpg");
        System.out.println(file.getName());
    }

    @Test
    public void testJsonnode() throws IOException {
        String us = "{'username':'7895'}";
        System.out.println(us);
    }

    @Test
    public void testExcelUtils(){
        String[] name = "啥接口的哈萨克".split("");
        String s = ExcelUtils.makeSampleFile(name, "C://fg759p//");
        System.out.println(s);
    }

    @Test
    public void testJbdcUtils() throws SQLException {
       /* String[] name = "熟地黄等会我".split("");
        String table = JdbcUtils.createTable(name);
        System.out.println(table);*/
        //String s = JdbcUtils.showTables();
    }

    @Test
    public void testString(){
        String name = "我是图片.jpg";
        String filename = UUIDUtils.getUUIDHex()+name.substring(name.indexOf("."));
        System.out.println(filename);
    }
}
