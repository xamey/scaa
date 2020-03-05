package mas.infrastructure.communication;

import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;
import java.util.Optional;

public interface ICommunication {

    void sendMessageBroadcast(IMessage message);

    void sendMessage(IMessage message);

    Optional<IMessage> receiveMessage(InfraAgentReference receiver);

    ArrayList<IMessage> receiveMessages(InfraAgentReference receiver);

}
