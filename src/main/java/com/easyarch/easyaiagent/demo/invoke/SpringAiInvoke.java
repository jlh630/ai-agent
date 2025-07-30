package com.easyarch.easyaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//
//@Component
//public class SpringAiInvoke implements CommandLineRunner {
//
//    @Resource
//    private ChatModel dashscopeChatModel;
//
//    @Override
//    public void run(String... args) throws Exception {
//        AssistantMessage output = dashscopeChatModel.call(new Prompt("你好，北京现在的时间"))
//                .getResult()
//                .getOutput();
//        System.out.println(output.getText());
//    }
//}
//
