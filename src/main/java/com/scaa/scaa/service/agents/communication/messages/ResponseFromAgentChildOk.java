package com.scaa.scaa.service.agents.communication.messages;

import mas.infrastructure.agent.InfraAgentReference;

public class ResponseFromAgentChildOk extends AbstractMessage {
    public ResponseFromAgentChildOk(InfraAgentReference emitter, InfraAgentReference receiver) {
        super(emitter, receiver);
    }
}
