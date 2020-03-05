package mas.infrastructure.directory;

import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.IMessage;

public interface IMessageAgentListener {

    void onMessageSent(InfraAgentReference sender, InfraAgentReference receiver, IMessage message);

    void onMessageReceived(InfraAgentReference sender, InfraAgentReference receiver, IMessage message);
}
