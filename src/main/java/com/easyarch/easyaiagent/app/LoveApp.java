package com.easyarch.easyaiagent.app;

import com.easyarch.easyaiagent.advisor.MyAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;


import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class LoveApp {

    private static final String SYSTEM_PROMPT = "你是一个温柔、善解人意的恋爱助手，擅长倾听、共情与提供建议。你的任务是陪伴用户，了解他们在恋爱中的困惑、喜悦与烦恼，并根据具体情况给予情感支持与建议。你说话的语气要亲切自然，略带一点可爱或浪漫的风格，不使用生硬或机械的语言。\n" + "\n" + "你会根据不同情境扮演不同角色，比如：\n" + "\n" + "需要建议时，你是恋爱导师；\n" + "需要倾诉时，你是知心朋友；\n" + "需要鼓励时，你是温暖的依靠。\n" + "你不会批判用户的情感选择，而是尊重他们的感受，用积极、建设性的方式引导他们思考和成长。你也可以适度调侃、撒娇或者讲些小玩笑，让对话更有趣、更贴近真实的恋爱互动。\n" + "\n" + "请始终保持耐心与温柔，成为用户心中那个特别的“恋爱组手”。";

    public ChatClient chatClient;

    /**
     * 初始化
     * @param chatClientBuilder
     */
    public LoveApp(ChatClient.Builder chatClientBuilder) {
        ChatMemory chatMemory = new InMemoryChatMemory();
        this.chatClient = chatClientBuilder.defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory),
                        new MyAdvisor()
                ).build();
    }

    /**
     * simple function
     * @param userPrompt
     * @param chatId
     * @return
     */
    public String toChat(String userPrompt, String chatId) {
        ChatResponse chatResponse = chatClient.prompt()
                .user(userPrompt)
                .advisors(advisor -> advisor.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY,1))

                .call()
                .chatResponse();
        return chatResponse.getResult().getOutput().getText();
    }
    record LoveReport(String title, List<String> suggestions) { }

    /**
     * 使用结构化输出
     * @param message
     * @param chatId
     * @return
     */
    public LoveReport doChatWriterReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveReport.class);
//        log.info("loveReport: {}", loveReport);
        return loveReport;
    }
}
