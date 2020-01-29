package main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import main.model.Task;

public class Storage {

    private static int idCounter = 0;
    private static ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();

    public static List<Task> getAllTasks() {
        if (tasks.size() == 0) {
            throw new NotFoundException("There are no tasks in the list");
        }
        return new ArrayList<>(tasks.values());
    }

    public synchronized static int addTask(Task task) {
        idCounter++;
        task.setId(idCounter);
        tasks.put(idCounter, task);
        return idCounter;
    }

    public static Task getTaskById(int id){
        Task t = tasks.get(id);
        if (t == null) {
            throw new NotFoundException("id " + id + " is not found");
        }
        return t;
    }

    public static void editTaskDescription(int id, String newDescription){
        Task task = getTaskById(id);
        task.setDescription(newDescription);
        tasks.replace(id, task);
    }

    public static int editTaskPriority(int id, int priority){
        Task task = getTaskById(id);
        if (!task.isDone()) {
            task.setPriority(priority);
            tasks.replace(id, task);
            return priority;
        }
        return 0;
    }

    public static void editTaskName(int id, String name){
        Task task = getTaskById(id);
        task.setTaskName(name);
        tasks.replace(id, task);
    }

    public static void editTaskStatus(int id, boolean status){
        Task task = getTaskById(id);
        task.setDone(status);
        tasks.replace(id, task);
    }

    public static void deleteTask(int id) {
        tasks.remove(id);
    }

    public static void deleteAllTask() {
        tasks.clear();
    }


}
