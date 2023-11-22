package com.example.joespetitions.model;

import java.util.ArrayList;
import java.util.List;

public class Petition {

    private String id;
    private String name;
    private String description;
    private List<User> signees;

    public Petition() {
        this.signees = new ArrayList<>();
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getSignees() {
        return signees;
    }

    public void setSignees(List<User> signees) {
        this.signees = signees;
    }

    // Method to add a signee to the petition
    public void addSignee(User signee) {
        this.signees.add(signee);
    }
}

