package cn.cdqf.dmsjportal.entity.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户参数类", description = "用于接收用户的实体类")
public class DmsjUserParam implements Serializable {
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名", name = "username", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", name = "password", required = true)
    private String password;

    @NotBlank(message = "电话不能为空")
    @ApiModelProperty(value = "电话", name = "phone", required = true)
    private String phone;
    private String address;
    private String idCard;
}
