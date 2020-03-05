package mas.infrastructure.directory;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.ICommunication;
import mas.infrastructure.communication.IMessage;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public interface IAgentDirectory extends ICommunication, IAgentDirectoryManager {

    ConcurrentMap<InfraAgentReference, ConcurrentLinkedQueue<IMessage>> getAgentsMessagesQueues();

    ConcurrentMap<InfraAgentReference, InfraAgent> getAgents();

}
