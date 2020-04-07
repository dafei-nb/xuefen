package cn.cdqf.dmsjportal.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisUtilsTest {
@Autowired
private RedisUtils redisUtils;
    @Test
    public void get() {
    redisUtils.set("fei","520");
    }

    @Test
    public void set() {
    }
}