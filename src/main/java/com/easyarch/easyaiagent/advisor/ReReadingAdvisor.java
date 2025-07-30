package com.easyarch.easyaiagent.advisor;

import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * re2 的发法 ai更加准确但是，会加大token的使用
 */
public class ReReadingAdvisor implements BaseAdvisor {

    private static final String DEFAULT_RE2_ADVISE_TEMPLATE = """
            {re2_input_query}
            Read the question again: {re2_input_query}
            """;


    private int order = 0;



    @Override
    public AdvisedRequest before(AdvisedRequest advisedRequest) {
        Map<String, Object> advisedUserParams = new HashMap<>(advisedRequest.userParams());
        advisedUserParams.put("xxx", advisedRequest.userText());

        return AdvisedRequest.from(advisedRequest)
                .userText("""
                        {xxx}
                        Read the question again: {xxx}
                        """)
                .userParams(advisedUserParams)
                .build();
    }
    @Override
    public AdvisedResponse after(AdvisedResponse advisedResponse) {
        return advisedResponse;
    }


    @Override
    public int getOrder() {
        return this.order;
    }

    public ReReadingAdvisor withOrder(int order) {
        this.order = order;
        return this;
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        return BaseAdvisor.super.aroundCall(advisedRequest, chain);
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        return BaseAdvisor.super.aroundStream(advisedRequest, chain);
    }

    @Override
    public String getName() {
        return BaseAdvisor.super.getName();
    }


    @Override
    public Scheduler getScheduler() {
        return BaseAdvisor.super.getScheduler();
    }
}