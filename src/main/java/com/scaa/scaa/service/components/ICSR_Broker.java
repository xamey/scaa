package com.scaa.scaa.service.components;

import com.scaa.scaa.model.Application;
import com.scaa.scaa.model.Component;
import com.scaa.scaa.model.ComponentServiceRelation;

import java.io.FileNotFoundException;
import java.util.List;

public interface ICSR_Broker {
    List<Application> getApplications();
    List<ComponentServiceRelation> getComponentServiceRelationList();
    void setApplication(Application application);
}
