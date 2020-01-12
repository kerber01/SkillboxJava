package response;

public class Task {

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

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
        if (done){
            setPriority(0);
        }
    }
}
