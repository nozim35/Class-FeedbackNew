package de.hawhamburg.classfee.feedback;

public class Module {
    private Long id;
    private String name;

    public Module(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}