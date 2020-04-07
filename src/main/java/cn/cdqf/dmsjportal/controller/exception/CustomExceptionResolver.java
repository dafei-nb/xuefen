package cn.cdqf.dmsjportal.controller.exception;

import cn.cdqf.dmsjportal.common.CustomException;
import cn.cdqf.dmsjportal.common.ResultEnum;
import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.properties.GlobleProperties;
import cn.cdqf.dmsjportal.util.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class CustomExceptionResolver {
    @Autowired
    private MailService mailService;
    @Autowired
    private GlobleProperties globleProperties;
    @ExceptionHandler({Exception.class})
    public ResultResponse exception(Exception e){
        e.printStackTrace();
        log.error("系统没有意识到的异常：{}",e);
        //邮箱发送 String subject,String content,String[] toWho
        StringBuilder stringBuilder = new StringBuilder("异常出现在：");
        stringBuilder.append(this.getClass().getName()).append(",异常信息为：").append(e);
        mailService.sendSimpleTextMailActual(globleProperties.getErrorMailSubject(),stringBuilder.toString()
        , ArrayUtils.add(new String[0],"1136153732@qq.com"));

        return ResultResponse.fail(e.getMessage());
    }

    //捕获异常
    @ExceptionHandler({CustomException.class})
    public ResultResponse customException(CustomException e){
        //出现异常 需要打印日志 为了给我们看
        log.info("项目出现了意识到的异常："+e.getMessage());



        //根据这个异常返回给前台提示信息
        return ResultResponse.fail(e.getMessage());
    }

}
