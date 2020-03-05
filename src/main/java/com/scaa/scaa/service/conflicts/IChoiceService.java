package com.scaa.scaa.service.conflicts;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;

import java.util.List;

public interface IChoiceService {
    List<Choice> getAllByRelation(ComponentServiceRelation componentServiceRelation);
    void saveOrUpdate(Choice choice);
}
