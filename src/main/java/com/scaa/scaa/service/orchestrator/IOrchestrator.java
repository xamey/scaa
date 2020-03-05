package com.scaa.scaa.service.orchestrator;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Possibility;

import java.util.List;

public interface IOrchestrator {
    ComponentServiceRelation resolveConflict
            (ComponentServiceRelation asker, List<ComponentServiceRelation> candidates) throws InterruptedException;
    Choice askForExpertChoice(List<Possibility> possibilities) throws InterruptedException;
}
