package com.scaa.scaa.service.agents.states;

import com.scaa.scaa.service.agents.communication.messages.AbstractMessage;
import com.scaa.scaa.service.agents.instances.ComponentAgent;
import com.scaa.scaa.service.agents.states.components.InitialComponentState;
import mas.infrastructure.state.LifeCycle;

import java.util.ArrayList;
import java.util.Collection;

public class ComponentAgentLifecycle extends LifeCycle {

    private ComponentAgent componentAgent;

    public ComponentAgentLifecycle() {
        super(new InitialComponentState());
    }

    public ArrayList<AbstractMessage> getAllMessages() {
        return new ArrayList<AbstractMessage>((Collection<? extends AbstractMessage>)
                getComponentAgent().readMessages().stream().filter(iMessage -> {
                    return iMessage instanceof AbstractMessage;
                }));
    }

    public ComponentAgent getComponentAgent() {
        return componentAgent;
    }

    public void setComponentAgent(ComponentAgent componentAgent) {
        this.componentAgent = componentAgent;
    }
}
