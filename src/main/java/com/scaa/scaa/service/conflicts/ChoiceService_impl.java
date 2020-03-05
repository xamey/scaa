package com.scaa.scaa.service.conflicts;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;
import com.scaa.scaa.repositories.IChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChoiceService_impl implements  IChoiceService{

    private IChoiceRepository iChoiceRepository;

    @Autowired
    public void setiChoiceRepository(IChoiceRepository iChoiceRepository) {
        this.iChoiceRepository = iChoiceRepository;
    }

    @Override
    public List<Choice> getAllByRelation(ComponentServiceRelation componentServiceRelation) {
        return iChoiceRepository.findAllByAskerEquals(componentServiceRelation);
    }

    @Override
    public void saveOrUpdate(Choice choice) {
        Optional<Choice> maybePresent = iChoiceRepository.findById(choice.getId());
        if (maybePresent.isPresent()) {
            Choice toUpdate = maybePresent.get();
            toUpdate.setAsker(choice.getAsker());
            toUpdate.setChosen(choice.getChosen());
            toUpdate.setDefinitive(choice.isDefinitive());
            iChoiceRepository.save(toUpdate);
        }
        else {
            iChoiceRepository.save(choice);
        }
    }
}
