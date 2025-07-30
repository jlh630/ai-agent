package com.easyarch.easyaiagent.advisor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

@Slf4j
public class MyAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {


    @Override
    public String getName() {
        return "jlh的advisor";
    }

    @Override
    public int getOrder() {
        return 500;
    }


    private AdvisedRequest before(AdvisedRequest request) {
        log.info("Request:Text -- {}", request.userText());
        return request;
    }

    private void after(AdvisedResponse chatClientResponse) {
        log.info("Response:Text --{}", chatClientResponse.response().getResult().getOutput().getText());
    }

    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        after(advisedResponse);
        return advisedResponse;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = before(advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponses, this::after);
    }
}
