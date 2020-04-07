package cn.cdqf.dmsjportal.security.mobile;

import cn.cdqf.dmsjportal.common.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhenzi.sms.ZhenziSmsClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 短信工具类
 */
@Component
@Slf4j
public class SMSUtil {
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private ObjectMapper objectMapper;

    @Async("getAsyncExecutor")
    public Future<Map<String,Object>> send(String phone){
        //int rannum=(int)((Math.random()*9+1)*100000);
        ZhenziSmsClient client = new ZhenziSmsClient(smsProperties.getAPIURL(), smsProperties.getAPPID(), smsProperties.getAPPSECRET());
        String result = null;
        try {
            result = client.send(phone, smsProperties.getINIT_WORD()+123456);
            System.out.println(result);
            HashMap hashMap = objectMapper.readValue(result, HashMap.class);
            hashMap.put(Constant.Security.DMSJ_PHONE_CODE,"123456");
            return new AsyncResult<>(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
