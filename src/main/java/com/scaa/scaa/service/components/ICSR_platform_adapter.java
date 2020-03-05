package com.scaa.scaa.service.components;

import com.scaa.scaa.model.Application;
import com.scaa.scaa.model.Component;
import com.scaa.scaa.model.ComponentServiceRelation;

import java.io.FileNotFoundException;
import java.util.List;

public interface ICSR_platform_adapter {
    List<Application> getApplicationsFromPlatform();
    List<ComponentServiceRelation> getComponentServiceRelationListFromPlatform();
    void setApplicationToPlatform(Application application);
}
