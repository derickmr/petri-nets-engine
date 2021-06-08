package com.unisinos.modelsimulator;

//TODO check duration logic
public class Process {
    private String name;
    private int processId;
    private double duration;
    private boolean active;

    public Process(String name) {
        this.name = name;
    }

    public Process(String name, double duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void activate(boolean active) {
        this.active = active;
    }
}
