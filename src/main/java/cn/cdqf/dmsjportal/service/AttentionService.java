package cn.cdqf.dmsjportal.service;

import cn.cdqf.dmsjportal.common.ResultResponse;
import cn.cdqf.dmsjportal.entity.param.UserAttentionParam;

public interface AttentionService {
    void add(String projectId, String userId);

    ResultResponse getAttentionByUser(String userId);

    UserAttentionParam queryByProjectId(String projectId);

    void cancelAttention(String projectId, String userId);

}
