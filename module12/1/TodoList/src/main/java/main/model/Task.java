package main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int priority;
    private String taskName;
    private String description;
    private boolean done;

    public Task(int priority, String taskName, String description) {
        this.priority = priority;
        this.taskName = taskName;
        this.description = description;
        this.done = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public synchronized void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public synchronized void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public synchronized void setPriority(int priority) {
        this.priority = priority;
    }

    public synchronized boolean isDone() {
        return done;
    }

    public synchronized void setDone(boolean done) {
        this.done = done;
        if (done) {
            setPriority(0);
        }
    }
}
