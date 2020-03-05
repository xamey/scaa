package com.scaa.scaa.service.agents.communication;

import mas.infrastructure.Infrastructure;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.ICommunication;
import mas.infrastructure.communication.IMessage;

import java.util.ArrayList;
import java.util.Optional;

public class CommunicationImp implements ICommunication {
    private Infrastructure infrastructure;

    public CommunicationImp(Infrastructure infrastructure) {
        this.infrastructure = infrastructure;
    }

    @Override
    public void sendMessageBroadcast(IMessage message) {
        infrastructure.sendMessageBroadcast(message);
    }

    @Override
    public void sendMessage(IMessage message) {
        infrastructure.sendMessage(message);
    }

    @Override
    public Optional<IMessage> receiveMessage(InfraAgentReference receiver) {
        return infrastructure.receiveMessage(receiver);
    }

    @Override
    public ArrayList<IMessage> receiveMessages(InfraAgentReference receiver) {
        return infrastructure.receiveMessages(receiver);
    }
}
