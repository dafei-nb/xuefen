package cn.cdqf.dmsjportal.controller;

import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.security.imagecode.ImageCode;
import cn.cdqf.dmsjportal.security.imagecode.ImageCodeGenerator;
import cn.cdqf.dmsjportal.security.mobile.SMSUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Controller
@Api("验证码生成接口")
public class CodeController {
@Autowired
private ImageCodeGenerator imageCodeGenerator;
@GetMapping("code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    //生成图片验证码
    ImageCode generate = imageCodeGenerator.generate();
    //保存验证码
    request.getSession().setAttribute(Constant.Security.IMAGE_CODE_KEY,generate);
    //把图片写过去
    ImageIO.write(generate.getBufferedImage(),  "JPG",response.getOutputStream());

}

    @Autowired
    private SMSUtil smsUtil;
    @GetMapping("code/mobile/{phone}")
    public void sendPhoneCode(@PathVariable("phone")String phone, HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException, ExecutionException, InterruptedException {
        //校验 应该校验该号码是否注册过
        Future<Map<String, Object>> send = smsUtil.send(phone);
        Map<String, Object> map = send.get();
        String phoneCode = (String)map.get(Constant.Security.DMSJ_PHONE_CODE);
        request.getSession().setAttribute(Constant.Security.DMSJ_PHONE_CODE,phoneCode);
    }
}
