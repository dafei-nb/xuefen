package cn.cdqf.dmsjportal.controller;

import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.controller.validator.BindingResultUtil;
import cn.cdqf.dmsjportal.entity.param.DmsjUserParam;
import cn.cdqf.dmsjportal.service.DmsjUserService;
import cn.cdqf.dmsjportal.util.ThreadLocalUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "用户注册接口")
public class RegisterController {
    @Autowired
    private DmsjUserService dmsjUserService;

    @PostMapping("register")
    @ApiOperation(value = "用户注册")
    public ResultResponse register(HttpServletRequest request,
                                   @Validated @RequestBody @ApiParam(value = "用户注册实体类",required = true)
                                   DmsjUserParam dmsjUserParam, BindingResult bindingResult
                                   ){
        ThreadLocalUtil.set(request.getRemoteAddr());
        BindingResultUtil.checkBindingResult(bindingResult);
        dmsjUserService.register(dmsjUserParam);
        return ResultResponse.success();

    }

}
