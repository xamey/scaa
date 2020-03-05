package com.scaa.scaa.service.agents.communication.messages;

import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;

public class ResponseForComponentServiceConnection extends AbstractMessage {

    public ResponseForComponentServiceConnection(InfraAgentReference emitter, InfraAgentReference receiver) {
        super(emitter, receiver);
    }
}
