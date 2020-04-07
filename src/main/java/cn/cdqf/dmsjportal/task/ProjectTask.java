package cn.cdqf.dmsjportal.task;

import cn.cdqf.dmsjportal.dao.DmsjProjectMapper;
import cn.cdqf.dmsjportal.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class ProjectTask {
    @Autowired
    private DmsjProjectMapper dmsjProjectMapper;
    private static final String PROJECTEXPIRE_KEY="PROJECTEXPIRE_KEY";
    //过期时间 需要对业务进行多次测试，然后再业务正常的时间内 计算出来一个合理的时间  EXPIRE_TIME<定时任务时间
    private static final long EXPIRE_TIME=5;
    @Autowired
    private RedisUtils redisUtils;
    @Scheduled(cron = "0/20 * * * * ?")
    @Async
    public void projectExpire(){

        boolean b = redisUtils.setnxAndExpire(PROJECTEXPIRE_KEY, "1", EXPIRE_TIME);
            //获得锁
            if (b) {
                log.info("定时任务开始执行");
                List<String> ids = dmsjProjectMapper.projectExpire();
                if (!CollectionUtils.isEmpty(ids)) {
                    dmsjProjectMapper.updateStatuByIds(ids, 2);
                }
                //ids:下面的业务  可以把这些信息 发给工作人员 这些项目过期了，请尽快通知发起人。。。。。
            }


    }


}
