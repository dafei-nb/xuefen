package cn.cdqf.dmsjportal.controller;

import cn.cdqf.dmsjportal.common.Constant;
import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.entity.DmsjUser;
import cn.cdqf.dmsjportal.service.AttentionService;
import cn.cdqf.dmsjportal.service.ShowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Api(value = "attention",description = "关注业务接口")
public class AttentionController {
    @Autowired
    private AttentionService attentionService;
    @PostMapping("attention/{projectId}")
    @ApiOperation(value="添加关注")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "projectId",type = "path",dataType = "string",required = true)}
    )
    public ResultResponse add(@PathVariable("projectId")String projectId, HttpSession httpSession){
        DmsjUser dmsjUser = (DmsjUser) httpSession.getAttribute(Constant.DmsjUser.LOGIN_USER_KEY);
        attentionService.add(projectId,dmsjUser.getUserId());
        return ResultResponse.success();
    }

    @ApiOperation(value="获得当前用户的关注信息")
    @GetMapping("attention")
    public ResultResponse attentionByUser(HttpSession httpSession){
        DmsjUser dmsjUser = (DmsjUser) httpSession.getAttribute(Constant.DmsjUser.LOGIN_USER_KEY);
        ResultResponse resultResponse =  attentionService.getAttentionByUser(dmsjUser.getUserId());
        return resultResponse;
    }

    @ApiOperation(value="取消关注")
    @DeleteMapping("attention/{projectId}")
    public ResultResponse cancelAttention(@PathVariable("projectId")String projectId, HttpSession httpSession){
        DmsjUser dmsjUser = (DmsjUser) httpSession.getAttribute(Constant.DmsjUser.LOGIN_USER_KEY);
        attentionService.cancelAttention(projectId,dmsjUser.getUserId());
        return ResultResponse.success();
    }

    /*@ApiOperation(value="查询")
    @DeleteMapping("attention/{projectId}")
    public ResultResponse getAttention(@PathVariable("projectId")String projectId, HttpSession httpSession){
        DmsjUser dmsjUser = (DmsjUser) httpSession.getAttribute(Constant.DmsjUser.LOGIN_USER_KEY);
        attentionService.cancelAttention(projectId,dmsjUser.getUserId());
        return ResultResponse.success();
    }*/

        /*@Autowired
        private ShowService showService;
        @GetMapping("show/{type}/{status}/{order}")
        @ApiOperation(value="获取页面")
        public ResultResponse getAll(@PathVariable("type") Integer type, @PathVariable("status") Integer status, @PathVariable("order") Integer order){
            return  showService.showAll(type,status,order);
        }*/

}
