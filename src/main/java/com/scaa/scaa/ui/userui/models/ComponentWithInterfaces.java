package userui.models;

import java.util.LinkedList;
import java.util.List;

public class ComponentWithInterfaces {

    private List<String> requiredInterfaces = new LinkedList<>();

    private List<String> providedInterfaces = new LinkedList<>();

    private String name;

    public ComponentWithInterfaces(String name) {
        this.name = name;
    }

    public ComponentWithInterfaces(String name, List<String> requiredInterfaces, List<String> providedInterfaces) {
        this(name);
        this.requiredInterfaces = requiredInterfaces;
        this.providedInterfaces = providedInterfaces;
    }

    public void addRequiredInterface(String name) {
        requiredInterfaces.add(name);
    }

    public void addProvidedInterface(String name) {
        providedInterfaces.add(name);
    }

    public List<String> getRequiredInterfaces() {
        return requiredInterfaces;
    }

    public List<String> getProvidedInterfaces() {
        return providedInterfaces;
    }

    public String getName() {
        return name;
    }

}