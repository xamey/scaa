package com.scaa.scaa.service.agents.communication.messages;

import com.scaa.scaa.model.ComponentServiceRelation;
import mas.infrastructure.agent.InfraAgentReference;

public class RequestedServiceResponse extends AbstractMessage {
    private boolean matched;
    private RequestForService request;

    public RequestedServiceResponse(RequestForService request, boolean matched) {
        super();
        this.matched = matched;
        this.request = request;
    }

    public RequestedServiceResponse(RequestForService request, boolean matched,
                                    InfraAgentReference emitter, InfraAgentReference receiver) {
        super(emitter, receiver);
        this.matched = matched;
    }

    public boolean isMatched() {
        return matched;
    }

    public RequestForService getRequest() {
        return request;
    }
}
