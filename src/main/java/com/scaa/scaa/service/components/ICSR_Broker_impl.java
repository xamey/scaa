package com.scaa.scaa.service.components;

import com.scaa.scaa.model.Application;
import com.scaa.scaa.model.Component;
import com.scaa.scaa.model.ComponentServiceRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;

@Service
public class ICSR_Broker_impl implements ICSR_Broker {

    private ICSR_platform_adapter ICSR_platform_adapter;

    public ICSR_Broker_impl() {
        ICSR_platform_adapter = new ICSR_platform_adapter_impl();
    }

    @Override
    public List<Application> getApplications() {
        return ICSR_platform_adapter.getApplicationsFromPlatform();
    }

    @Override
    public List<ComponentServiceRelation> getComponentServiceRelationList() {
        return ICSR_platform_adapter.getComponentServiceRelationListFromPlatform();
    }

    @Override
    public void setApplication(Application application) {
        ICSR_platform_adapter.setApplicationToPlatform(application);
    }
}
