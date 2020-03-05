package com.scaa.scaa.service.agents.communication.messages;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.IMessage;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMessage implements IMessage {

    private InfraAgentReference emitter;
    private List<InfraAgentReference> receivers = new ArrayList<>();

    protected AbstractMessage(InfraAgentReference emitter) {
        this.emitter = emitter;
    }

    protected AbstractMessage(InfraAgentReference emitter, List<InfraAgentReference> receivers){
        this.emitter = emitter;
        this.receivers = receivers;
    }

    public AbstractMessage(InfraAgentReference emitter, InfraAgentReference receiver) {
        receivers.add(receiver);
        this.emitter = emitter;
    }

    public AbstractMessage() { }

    @Override
    public InfraAgentReference getEmitter() {
        return emitter;
    }

    @Override
    public void setEmitter(InfraAgentReference emitter) {
        this.emitter = emitter;
    }

    @Override
    public List<InfraAgentReference> getReceivers() {
        return receivers;
    }

    @Override
    public void setReceivers(ArrayList<InfraAgentReference> receivers) {
        this.receivers = receivers;
    }
}
