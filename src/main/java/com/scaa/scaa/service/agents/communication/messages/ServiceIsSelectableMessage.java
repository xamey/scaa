package com.scaa.scaa.service.agents.communication.messages;

import mas.infrastructure.agent.InfraAgentReference;

public class ServiceIsSelectableMessage extends AbstractMessage {
    private boolean isSelectable;

    public ServiceIsSelectableMessage(InfraAgentReference emitter, InfraAgentReference receiver, boolean isSelectable) {
        super(emitter, receiver);
        this.isSelectable = isSelectable;
    }

    public boolean isSelectable() {
        return isSelectable;
    }
}
