package com.scaa.scaa.model;

import java.util.List;

public class Application {
    List<ComponentServiceRelation> componentServiceRelationList;

    public Application(List<ComponentServiceRelation> componentServiceRelationList) {
        this.componentServiceRelationList = componentServiceRelationList;
    }

    public List<ComponentServiceRelation> getComponentServiceRelationList() {
        return componentServiceRelationList;
    }

    public void setComponentServiceRelationList(List<ComponentServiceRelation> componentServiceRelationList) {
        this.componentServiceRelationList = componentServiceRelationList;
    }
}
