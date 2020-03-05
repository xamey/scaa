package com.scaa.scaa.service.agents.states;

import com.scaa.scaa.service.agents.communication.messages.AbstractMessage;
import com.scaa.scaa.service.agents.instances.ServiceAgent;
import com.scaa.scaa.service.agents.states.services.InitialServiceState;
import mas.infrastructure.state.LifeCycle;

import java.util.ArrayList;
import java.util.Collection;

public class ServiceAgentLifecycle extends LifeCycle {
    private ServiceAgent agent;

    public ServiceAgentLifecycle() {
        super(new InitialServiceState());
    }

    public ServiceAgentLifecycle(ServiceAgent agent) {
        this();
        this.agent = agent;
    }

    public ServiceAgent getAgent() {
        return agent;
    }

    public void setAgent(ServiceAgent agent) {
        this.agent = agent;
    }

    public ArrayList<AbstractMessage> getAllMessages() {
        return new ArrayList<AbstractMessage>((Collection<? extends AbstractMessage>)
                getAgent().readMessages().stream().filter(iMessage -> {
                    return iMessage instanceof AbstractMessage;
                }));
    }
}
