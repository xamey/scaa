package com.scaa.scaa.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    boolean definitive;

    @ManyToOne
    ComponentServiceRelation asker;

    @ManyToOne
    ComponentServiceRelation chosen;

    public boolean isDefinitive() {
        return definitive;
    }

    public void setDefinitive(boolean definitive) {
        this.definitive = definitive;
    }


    public ComponentServiceRelation getAsker() {
        return asker;
    }

    public void setAsker(ComponentServiceRelation asker) {
        this.asker = asker;
    }

    public ComponentServiceRelation getChosen() {
        return chosen;
    }

    public void setChosen(ComponentServiceRelation chosen) {
        this.chosen = chosen;
    }

    public Choice(boolean definitive, ComponentServiceRelation asker, ComponentServiceRelation chosen) {
        this.definitive = definitive;
        this.asker = asker;
        this.chosen = chosen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Choice choice = (Choice) o;
        return definitive == choice.definitive &&
                Objects.equals(asker, choice.asker) &&
                Objects.equals(chosen, choice.chosen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(definitive, asker, chosen);
    }
}
