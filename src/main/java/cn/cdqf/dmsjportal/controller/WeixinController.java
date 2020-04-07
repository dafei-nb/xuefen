package cn.cdqf.dmsjportal.controller;

import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.entity.DmsjUser;
import cn.cdqf.dmsjportal.entity.DmsjWx;
import cn.cdqf.dmsjportal.entity.param.WeiXinParam;
import cn.cdqf.dmsjportal.service.DmsjUserService;
import cn.cdqf.dmsjportal.util.HttpClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@Controller
@Slf4j
public class WeixinController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DmsjUserService dmsjUserService;
    //创建应用的时候 得到应用id
    public  final static String APPID = "wxd853562a0548a7d0";

    //用户授权后微信的回调域名 http://bugtracker.itsource.cn/callback -- http://localhost:80/callback
    public final static String CALLBACK="http://bugtracker.itsource.cn/callback";

    //网站应用 必须是这个值
    public final static String SCOPE = "snsapi_login";
    //应用的安全码
    public final static String APPSECRET = "4a5d5615f93f24bdba2ba8534642dbb6";

    //微信上获取code的地址
    public final static String CODEURL = "https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    //微信上获取at accesstoken的地址
    public final static String ACCESSTOKEURL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //微信上获取用户信息的地址
    public final static String USERINFOURL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    @GetMapping("wx/callback")
    //微信回调会传递授权码code
    public String callback(String code,String state) throws Exception {
        //用户禁止授权
        if(code==null){
            return null;
        }
        log.info("回调了....:"+code);
        //获得令牌
        String accessTokenUrl = ACCESSTOKEURL.replace("APPID",APPID).replace("SECRET",APPSECRET)
                .replace("CODE",code).trim();
        String accessToken = HttpClientUtils.httpGet(accessTokenUrl, null);
        log.info("获得的令牌为："+accessToken);
        //获得令牌值
        String access_token = (String)objectMapper.readValue(accessToken, HashMap.class).get("access_token");
        String openid = (String)objectMapper.readValue(accessToken, HashMap.class).get("openid");
        //openid其实是用户的唯一标识：一个用户在同一个应用中 openid是唯一的，但是在不同应用是不同的  自己可以携带一下
        String userinfo = HttpClientUtils.httpGet(USERINFOURL.replace("ACCESS_TOKEN", access_token).replace("OPENID",openid),null);
        log.info("获得用户信息为："+userinfo);
        //调用WeiXinService.queryByOpenId()
        WeiXinParam weiXinParam = objectMapper.readValue(userinfo, WeiXinParam.class);

        //1.当前微信已经绑定了账户，
        DmsjWx dmsjWx = dmsjUserService.selectByOpenId(openid);

        /*DmsjUser userByUserId = dmsjUserService.getUserByUserId(userid);
        String username = userByUserId.getUsername();
        String password = userByUserId.getPassword();*/
        if (dmsjWx!=null){
            String userid=dmsjWx.getUserId();
            if (userid!=null){
                return "redirect:/weixin/login?userid="+userid+"";
            }
            }

        // 2.没有绑定用户,并且没有登录过该网站insert一条数据，还是认为用户登录了 重定向 /authentication/wx
        //前台展示什么
        if(dmsjWx==null){
            DmsjWx dmsjWx1=DmsjWx.builder().openid(weiXinParam.getOpenid()).nickname(weiXinParam.getNickname()).sex(weiXinParam.getSex()).province(weiXinParam.getProvince()).city(weiXinParam.getCity()).country(weiXinParam.getCountry()).headimgurl(weiXinParam.getHeadimgurl()).union(weiXinParam.getUnionid()).type(weiXinParam.getLogintype()).build();
           dmsjUserService.insertWx(dmsjWx1);
        }
        //logout  记住我cookie
        return "redirect:/weixin/wx?openid="+openid+"";
    }
    @GetMapping("wx/test")
    @ResponseBody
    public String test(){
        return "登录成功";
    }
}
