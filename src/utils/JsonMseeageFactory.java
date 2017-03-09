package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.ResultJson;

/**
 * @author Chenli
 * @time 2017/3/7 17:12
 */
public class JsonMseeageFactory {

   public static String makeSuccessMsg(String msg){
       ObjectMapper mapper = new ObjectMapper();
       try {
           return mapper.writeValueAsString(new ResultJson(1,msg));
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }
       return "success";
   }

   public static String makeErroMsg(String msg){
       ObjectMapper mapper = new ObjectMapper();
       try {
           return mapper.writeValueAsString(new ResultJson(0,msg));
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }
       return "error";
   }
}
