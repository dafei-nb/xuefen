package cn.cdqf.dmsjportal.entity.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAttentionParam implements Serializable {

    private String projectId;

    private String projectName;

    private int  completion;

    private int residueDay;

    private int supportMoney;

    private int supporter;

    private int attention;

}
