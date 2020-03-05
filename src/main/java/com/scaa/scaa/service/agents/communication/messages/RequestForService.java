package com.scaa.scaa.service.agents.communication.messages;

import com.scaa.scaa.model.Service;
import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;
import java.util.List;

public class RequestForService extends AbstractMessage {
    private Service requestedService;
    private boolean required;

    public RequestForService(InfraAgentReference emitter, List<InfraAgentReference> receivers,
                             Service requestedService, boolean required) {
        super(emitter, receivers);
        this.requestedService = requestedService;
        this.required = required;
    }

    public RequestForService(InfraAgentReference emitter, Service requestedService, boolean required) {
        super(emitter);
        this.requestedService = requestedService;
        this.required = required;
    }

    public Service getRequestedService() {
        return requestedService;
    }

    public void setRequestedService(Service requestedService) {
        this.requestedService = requestedService;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isGiven() {
        return !required;
    }
}
