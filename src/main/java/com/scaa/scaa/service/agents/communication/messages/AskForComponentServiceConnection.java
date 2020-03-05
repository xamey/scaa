package com.scaa.scaa.service.agents.communication.messages;

import com.scaa.scaa.model.Service;
import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;

public class AskForComponentServiceConnection extends AbstractMessage {

    private Service service;
    private boolean required;

    public AskForComponentServiceConnection(InfraAgentReference emitter, Service service, boolean required) {
        super(emitter, (ArrayList<InfraAgentReference>) null);
        this.service = service;
        this.required = required;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
