package mas.infrastructure.communication;

import mas.infrastructure.agent.InfraAgentReference;

import java.util.ArrayList;
import java.util.List;

public interface IMessage {

    InfraAgentReference getEmitter();

    void setEmitter(InfraAgentReference emitter);

    List<InfraAgentReference> getReceivers();

    void setReceivers(ArrayList<InfraAgentReference> receivers);

}
