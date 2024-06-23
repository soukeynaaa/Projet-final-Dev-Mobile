package com.example.todoapp.ui.theme;

import java.util.UUID;

public class Task {
    private String id;
    private String name;
    private String description;
    private int color;
    private String status;

    public Task(String name, String description, int color, String status) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.color = color;
        this.status = status;
    }

    public String getId() {
        return id;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
