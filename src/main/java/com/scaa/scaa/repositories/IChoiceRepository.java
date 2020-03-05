package com.scaa.scaa.repositories;

import com.scaa.scaa.model.Choice;
import com.scaa.scaa.model.ComponentServiceRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findAllByAskerEquals(ComponentServiceRelation componentServiceRelation);
    Choice save(Choice choice);
}
