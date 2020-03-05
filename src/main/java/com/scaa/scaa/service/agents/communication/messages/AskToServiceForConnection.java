package com.scaa.scaa.service.agents.communication.messages;

import com.scaa.scaa.model.Service;
import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;

public class AskToServiceForConnection extends AbstractMessage {

    public AskToServiceForConnection(InfraAgentReference emitter, InfraAgentReference receiver) {
        super(emitter, receiver);
    }



}
