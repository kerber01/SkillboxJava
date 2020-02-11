package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(readOnly = true)
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/tasks/")
    @Transactional(readOnly = false)
    public int addTask(Task task) {
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }

    @GetMapping("/tasks/")
    public List<Task> getAllTasks() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> taskList = new ArrayList<>();

        for (Task task : taskIterable) {
            taskList.add(task);
        }
        return taskList;
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable int id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) {
            throw new NotFoundException("id " + id + " is not found");
        }
        return optionalTask.get();
    }

    @PutMapping("/tasks/{id}/desc")
    @Transactional(readOnly = false)
    public void editTaskDescription(@PathVariable int id, String newDescription) {
        Task newDescTask = getTaskById(id);
        newDescTask.setDescription(newDescription);
        taskRepository.save(newDescTask);
    }

    @PutMapping("/tasks/{id}/priority")
    @Transactional(readOnly = false)
    public int editTaskPriority(@PathVariable int id, int priority) {
        Task task = getTaskById(id);
        if (!task.isDone()) {
            task.setPriority(priority);
            taskRepository.save(task);
            return priority;
        }
        return 0;
    }

    @PutMapping("/tasks/{id}/name")
    @Transactional(readOnly = false)
    public void editTaskName(@PathVariable int id, String name) {
        Task newNameTask = getTaskById(id);
        newNameTask.setTaskName(name);
        taskRepository.save(newNameTask);
    }

    @PutMapping("/tasks/{id}/status")
    @Transactional(readOnly = false)
    public void editTaskStatus(@PathVariable int id, boolean status) {
        Task newStatusTask = getTaskById(id);
        newStatusTask.setDone(status);
        taskRepository.save(newStatusTask);
    }

    @DeleteMapping("/tasks/{id}")
    @Transactional(readOnly = false)
    public void deleteTask(@PathVariable int id) {
        taskRepository.delete(getTaskById(id));
    }

    @DeleteMapping("/tasks/")
    @Transactional(readOnly = false)
    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }

}
