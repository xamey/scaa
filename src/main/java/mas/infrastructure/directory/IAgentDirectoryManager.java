package mas.infrastructure.directory;

import mas.infrastructure.agent.InfraAgent;
import mas.infrastructure.agent.InfraAgentReference;


public interface IAgentDirectoryManager {

    void addAgent(InfraAgent infraAgent);

    void removeAgent(InfraAgentReference infraAgentReference);
}
