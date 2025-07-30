package com.easyarch.easyaiagent.app;


import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;


@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void testToChat() {
        String uid = UUID.randomUUID().toString();
        System.out.println(loveApp.toChat("你好,我是xx", uid));
//        System.out.println(loveApp.toChat("我想找另一个伴侣", uid));
//        System.out.println(loveApp.toChat("你好,我是谁?,刚刚告诉过你了", uid));
    }
    @Test
    void testDoChatWriterReport(){
        String uid = UUID.randomUUID().toString();
        System.out.println(loveApp.doChatWriterReport("你好我是xx，我希望能够找到另一半有什么的建议", uid));
        System.out.println(loveApp.doChatWriterReport("你好我是xx，我希望能够找到另一半有什么的建议", uid));
        System.out.println(loveApp.doChatWriterReport("你好我是xx，我希望能够找到另一半有什么的建议", uid));

    }
}