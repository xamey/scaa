package com.scaa.scaa.service.conflicts;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.model.Possibility;
import com.scaa.scaa.service.orchestrator.IOrchestrator;
import com.scaa.scaa.service.orchestrator.Orchestrator_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConflictResolver_impl implements IConflictResolver {

    private ChoiceService_impl choiceService_impl;

    private Orchestrator_impl orchestrator_impl;


    @Autowired
    public void setChoiceService_impl(ChoiceService_impl choiceService_impl) {
        this.choiceService_impl = choiceService_impl;
    }

    @Autowired
    public void setOrchestrator_impl(Orchestrator_impl orchestrator_impl) {
        this.orchestrator_impl = orchestrator_impl;
    }

    @Override
    public ComponentServiceRelation resolveConflict(ComponentServiceRelation asker, List<ComponentServiceRelation> candidates) throws InterruptedException {
        List<Choice> alreadyMadeChoices = choiceService_impl.getAllByRelation(asker);
        List<ComponentServiceRelation> alreadySavedCSR = new ArrayList<>();
        for (ComponentServiceRelation currentCandidate : candidates) {
            for (Choice currentChoice : alreadyMadeChoices) {
                if (currentChoice.getChosen().equals(currentCandidate)) {
                    if (currentChoice.isDefinitive()) return currentCandidate;
                    alreadySavedCSR.add(currentCandidate);
                }
            }
        }
        List<Possibility> possibilities = new ArrayList<>();
        for (ComponentServiceRelation csr : candidates) {
            List<ComponentServiceRelation> candidatesForAnApplication = new ArrayList<>();
            candidatesForAnApplication.add(asker);
            candidatesForAnApplication.add(csr);
            possibilities.add(new Possibility(asker, csr, !alreadySavedCSR.contains(csr)));
        }
        Choice returnedChoice = orchestrator_impl.askForExpertChoice(possibilities);
        if (returnedChoice.isDefinitive()) choiceService_impl.saveOrUpdate(returnedChoice);
        return returnedChoice.getChosen();
    }
}
