package com.sky;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class MeetingRoomManagementApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(DigestUtils.md5DigestAsHex("admin123".getBytes()));
    }

}
