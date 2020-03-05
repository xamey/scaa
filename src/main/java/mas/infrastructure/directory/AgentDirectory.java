package mas.infrastructure.directory;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;
import mas.infrastructure.communication.IMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class AgentDirectory implements IAgentDirectory {

    private final List<IAgentListener> agentListeners;
    private final List<IReferenceAgentListener> referenceAgentListeners;
    private final List<IMessageAgentListener> messageAgentListeners;
    private final ConcurrentMap<InfraAgentReference, InfraAgent> agents; // references des agents à l'instant t
    private final ConcurrentMap<InfraAgentReference, ConcurrentLinkedQueue<IMessage>> agentsMessagesQueues; // references des agents associés aux messages reçus
    private final ConcurrentMap<InfraAgentReference, ReadWriteLock> agentsLocks;

    private AgentDirectory() {
        referenceAgentListeners = Collections.synchronizedList(new ArrayList<>());
        agentListeners = Collections.synchronizedList(new ArrayList<>());
        messageAgentListeners = Collections.synchronizedList(new ArrayList<>());
        agents = new ConcurrentHashMap<>();
        agentsMessagesQueues = new ConcurrentHashMap<>();
        agentsLocks = new ConcurrentHashMap<>();
    }

    public static AgentDirectory getInstance() {
        return AgentDirectoryHolder.instance;
    }

    public ConcurrentMap<InfraAgentReference, InfraAgent> getAgents() {
        return agents;
    }

    @Override
    public void addAgent(InfraAgent infraAgent) {
        agentsLocks.put(infraAgent.getInfraAgentReference(), new ReentrantReadWriteLock());
        agentWriteLock(infraAgent.getInfraAgentReference());
        agents.put(infraAgent.getInfraAgentReference(), infraAgent);
        agentsMessagesQueues.put(infraAgent.getInfraAgentReference(), new ConcurrentLinkedQueue<>());
        agentWriteUnlock(infraAgent.getInfraAgentReference());
        referenceAgentListeners.forEach(agentListener -> agentListener.addAgent(infraAgent.getInfraAgentReference()));
        agentListeners.forEach(agentListener -> agentListener.addAgent(infraAgent));
    }

    @Override
    public void removeAgent(InfraAgentReference infraAgentReference) {

        agentListeners.forEach(agentListener -> agentListener.removeAgent(agents.get(infraAgentReference)));
        agentWriteLock(infraAgentReference);
        agents.remove(infraAgentReference);
        agentsMessagesQueues.remove(infraAgentReference);
        agentWriteUnlock(infraAgentReference);
        referenceAgentListeners.forEach(agentListener -> agentListener.removeAgent(infraAgentReference));
    }

    public ConcurrentMap<InfraAgentReference, ConcurrentLinkedQueue<IMessage>> getAgentsMessagesQueues() {
        return agentsMessagesQueues;
    }

    @Override
    public void sendMessage(IMessage message) {
        int index;
        for (index = 0; index < message.getReceivers().size(); index++) {
            agentReadLock(message.getReceivers().get(index));
            if (agentsMessagesQueues.containsKey(message.getReceivers().get(index))) {
                agentsMessagesQueues.get(message.getReceivers().get(index)).add(message);
                int finalIndex = index;
                messageAgentListeners.forEach(messageAgentListener -> messageAgentListener.onMessageSent(message.getEmitter(),
                        message.getReceivers().get(finalIndex), message));
            }
            agentReadUnlock(message.getReceivers().get(index));
        }
    }

    @Override
    public void sendMessageBroadcast(IMessage message) {
        agentsMessagesQueues.keySet().forEach(this::agentReadLock);
        agentsMessagesQueues.forEach((key, value) -> {
            if (key != message.getEmitter()) {
                value.add(message);
                notifyMessageAgentListeners(message.getEmitter(), message, key);
            }
        });
        agentsMessagesQueues.keySet().forEach(this::agentReadUnlock);
    }

    private void notifyMessageAgentListeners(InfraAgentReference sender, IMessage IMessage,
                                             InfraAgentReference infraAgentReference) {
        messageAgentListeners.forEach(
                messageAgentListener -> messageAgentListener.onMessageSent(sender, infraAgentReference, IMessage));
    }

    @Override
    public Optional<IMessage> receiveMessage(InfraAgentReference receiver) {
        agentReadLock(receiver);
        Optional<IMessage> message = Optional.ofNullable(agentsMessagesQueues.get(receiver))
                .map(ConcurrentLinkedQueue::poll);
        message.ifPresent(messageAgent -> notifyMessageAgentListeners(messageAgent.getEmitter(), messageAgent,
                receiver));
        agentReadUnlock(receiver);
        return message;
    }

    @Override
    public ArrayList<IMessage> receiveMessages(InfraAgentReference receiver) {
        ArrayList<IMessage> messages = new ArrayList<>(agentsMessagesQueues.get(receiver));
        agentsMessagesQueues.get(receiver).clear();
        return messages;
    }

    private void agentWriteLock(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.writeLock().lock());
    }

    private void agentReadLock(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.readLock().lock());
    }

    private void agentWriteUnlock(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.writeLock().unlock());
    }

    private void agentReadUnlock(InfraAgentReference infraAgentReference) {
        executeIfPresent(agentsLocks.get(infraAgentReference), readWriteLock -> readWriteLock.readLock().unlock());
    }

    private <T> void executeIfPresent(T object, Consumer<T> objectConsumer) {
        if (object != null) {
            objectConsumer.accept(object);
        }
    }

    public List<IMessageAgentListener> getMessageAgentListeners() {
        return messageAgentListeners;
    }

    private static class AgentDirectoryHolder {
        private final static AgentDirectory instance = new AgentDirectory();
    }

}