package com.scaa.scaa.service.conflicts;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;

import java.util.List;

public interface IConflictResolver {
    ComponentServiceRelation resolveConflict
            (ComponentServiceRelation asker, List<ComponentServiceRelation> candidates) throws InterruptedException;
}
