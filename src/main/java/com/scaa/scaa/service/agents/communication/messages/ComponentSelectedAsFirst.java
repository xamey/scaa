package com.scaa.scaa.service.agents.communication.messages;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;
import java.util.Arrays;

public class ComponentSelectedAsFirst extends AbstractMessage {
    public ComponentSelectedAsFirst(InfraAgentReference receiver) {
        super();
        ArrayList<InfraAgentReference> receivers = new ArrayList<>();
        receivers.add(receiver);
        setReceivers(receivers);
    }
}
