package mas.infrastructure.directory;

import mas.infrastructure.agent.InfraAgent;

public interface IAgentListener {

    void addAgent(InfraAgent infraAgent);

    void removeAgent(InfraAgent infraAgent);
}
