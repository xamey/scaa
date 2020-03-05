package mas.infrastructure.directory;

import mas.infrastructure.agent.InfraAgentReference;

public interface IReferenceAgentListener {

    void addAgent(InfraAgentReference infraAgentReference);

    void removeAgent(InfraAgentReference infraAgentReference);
}
