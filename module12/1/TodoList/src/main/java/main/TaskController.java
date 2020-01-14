package main;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import response.Task;

@RestController
public class TaskController {


    @PostMapping("/tasks/")
    public int addTask(Task task) {

        return Storage.addTask(task);
    }

    @GetMapping("/tasks/")
    public List<Task> getAllTasks() {
        return Storage.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id) {
        Task task = Storage.getTaskById(id);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<Task>(task, HttpStatus.OK);
    }

    @PutMapping("/tasks/{id}/desc")
    public void editTaskDescription(@PathVariable int id, String newDescription) {
        Storage.editTaskDescription(id, newDescription);
    }

    @PutMapping("/tasks/{id}/priority")
    public int editTaskPriority(@PathVariable int id, int priority) {
        return Storage.editTaskPriority(id, priority);
    }

    @PutMapping("/tasks/{id}/name")
    public void editTaskName(@PathVariable int id, String name) {
        Storage.editTaskName(id, name);
    }

    @PutMapping("/tasks/{id}/status")
    public void editTaskStatus(@PathVariable int id, boolean status) {
        Storage.editTaskStatus(id, status);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTask(@PathVariable int id) {
        Storage.deleteTask(id);
    }

    @DeleteMapping("/tasks/")
    public void deleteAllTasks() {
        Storage.deleteAllTask();
    }

}
