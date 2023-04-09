package com.example.app_supportpolywork.data.model.support_model;

import java.io.Serializable;

public class Filter implements Serializable {
    private Position position;
    private Technology technology;
    private Salary salary;
    private ExperienceFilter experienceFilter;

    public Filter() {
    }

    public Filter(Position position, Technology technology, Salary salary, ExperienceFilter experienceFilter) {
        this.position = position;
        this.technology = technology;
        this.salary = salary;
        this.experienceFilter = experienceFilter;
    }

    public ExperienceFilter getExperienceFilter() {
        return experienceFilter;
    }

    public void setExperienceFilter(ExperienceFilter experienceFilter) {
        this.experienceFilter = experienceFilter;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }
}
