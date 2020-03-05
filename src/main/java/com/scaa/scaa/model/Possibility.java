package com.scaa.scaa.model;

import java.util.List;

public class Possibility {
    ComponentServiceRelation asker;
    ComponentServiceRelation candidate;
    boolean preferable;

    public ComponentServiceRelation getAsker() {
        return asker;
    }

    public void setAsker(ComponentServiceRelation asker) {
        this.asker = asker;
    }

    public ComponentServiceRelation getCandidate() {
        return candidate;
    }

    public void setCandidate(ComponentServiceRelation candidate) {
        this.candidate = candidate;
    }

    public boolean isPreferable() {
        return preferable;
    }

    public void setPreferable(boolean preferable) {
        this.preferable = preferable;
    }

    public Possibility(ComponentServiceRelation asker, ComponentServiceRelation candidate, boolean preferable) {
        this.asker = asker;
        this.candidate = candidate;
        this.preferable = preferable;
    }
}
