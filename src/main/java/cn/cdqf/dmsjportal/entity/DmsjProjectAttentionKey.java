package cn.cdqf.dmsjportal.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class DmsjProjectAttentionKey implements Serializable {
    private String userId;

    private String projectId;


}