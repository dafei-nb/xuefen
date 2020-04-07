package cn.cdqf.dmsjportal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmsjUser {
    private Integer id;

    private String userId;

    private String username;

    private String password;

    private String phone;

    private String registerIp;

    private Date registerTime;

    private String loginIp;

    private Date loginTime;

    private String address;

    private String idCard;

    private Integer status=0;


}